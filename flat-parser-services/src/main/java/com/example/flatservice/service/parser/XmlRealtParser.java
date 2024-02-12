package com.example.flatservice.service.parser;


import com.example.flatservice.core.entity.ParsedFlatEntity;
import com.example.flatservice.exceptions.ParserException;
import com.example.flatservice.service.api.FlatParser;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;

@Service
public class XmlRealtParser implements FlatParser {

    public ParsedFlatEntity parse(String url) throws ParserException {
        return parseRealt(createDocument(url));
    }

    private Document createDocument(String url) throws ParserException {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return db.parse(url);
        } catch (Exception e) {
            throw new ParserException("Не удалось получить XML документ");
        }
    }

    private ParsedFlatEntity parseRealt(Document doc){
        if (doc == null) return null;

        Node rootNode = doc.getDocumentElement();

        Node settings = GetNodeByAttribute(rootNode, "ul", "class", "w-full -my-1");
        if (settings == null) return null;

        Node name = GetNodeByName(rootNode, "h1");
        if (name == null) return null;

        Node cost = GetNodeByName(rootNode, "h2");
        if (cost == null) return null;

        try {
            HashMap<String, String> settingsValue = GetSettings(settings);
            String nameValue = GetValueFromNode(name).replace("\t", "").replace("\n", "");
            String costValue = GetValueFromNode(cost).replace("\t", "").replace("\n", "");

            return new ParsedFlatEntity(nameValue, costValue, settingsValue);
        }
        catch (ParserException ex){
            return null;
        }
    }

    private Node GetNodeByAttribute(Node root, String nodeName, String attrName, String attValue) {
        NodeList list = root.getChildNodes();
        int len = list.getLength();
        for (int i = 0; i < len; i++) {
            Node node = list.item(i);
            if (node.getNodeName().equals(nodeName)
                    && node.hasAttributes()) {
                Node attr = node.getAttributes().getNamedItem(attrName);
                if (attr == null) continue;
                if (attr.getNodeValue().equals(attValue))
                    return node;
            } else {
                Node child = GetNodeByAttribute(node, nodeName, attrName, attValue);
                if (child != null) return child;
            }
        }

        return null;
    }

    private Node GetNodeByName(Node root, String nodeName) {
        NodeList list = root.getChildNodes();
        int len = list.getLength();
        for (int i = 0; i < len; i++) {
            Node node = list.item(i);
            if (node.getNodeName().equals(nodeName)) {
                return node;
            } else {
                Node child = GetNodeByName(node, nodeName);
                if (child != null) return child;
            }
        }

        return null;
    }

    private HashMap<String, String> GetSettings(Node node) {
        HashMap<String, String> result = new HashMap<>();

        NodeList list = node.getChildNodes();
        int len = list.getLength();
        for (int i = 0; i < len; i++) {
            Node child = list.item(i);
            if (!child.getNodeName().equals("li")) continue;
            try {
                KeyValuePair<String, String> keyValuePair = GetSetting(child);
                result.put(keyValuePair.key, keyValuePair.value);
            } catch (Exception ex) {
            }
        }

        return result;
    }

    private KeyValuePair<String, String> GetSetting(Node node) throws ParserException {

        var body = GetChildByName(node, "div", 0);

        if (body == null) throw new ParserException();

        var titleContainer = GetChildByName(body, "div", 0);
        var valueContainer = GetChildByName(body, "div", 1);

        if (titleContainer == null || valueContainer == null) throw new ParserException();

        var key = GetChildByName(titleContainer, "span", 0);
        var value = GetChildByName(valueContainer, "p", 0);

        if (key == null || value == null) throw new ParserException();

        return new KeyValuePair<>(GetValueFromNode(key), GetValueFromNode(value));
    }

    private Node GetChildByName(Node node, String name, int index) {
        var children = node.getChildNodes();
        int len = children.getLength();
        int counter = 0;
        for (int i = 0; i < len; i++) {
            if (children.item(i).getNodeName().equals(name)) {
                if (counter == index)
                    return children.item(i);
                else
                    counter++;
            }

        }
        return null;
    }

    private String GetValueFromNode(Node node) throws ParserException {
        String result = node.getNodeValue();
        if (result != null) return result;

        var content = node.getFirstChild();
        if (content.getNodeType() == Node.TEXT_NODE)
            return content.getNodeValue();

        throw new ParserException();
    }

    private static class KeyValuePair<K, T> {
        public K key;
        public T value;

        public KeyValuePair(K key, T value) {
            this.key = key;
            this.value = value;
        }
    }
}
