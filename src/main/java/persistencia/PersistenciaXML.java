package persistencia;

import contatos.Contato;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaXML implements IPersistencia{
    public String filePath = "data.xml";
    @Override
    public void salvar(Contato contato) {
        File data = new File(filePath);

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = domFactory.newDocumentBuilder();
            Document doc;
            Element rootElement;

            if (data.exists() && data.length() > 40) {
                doc = docBuilder.parse(data);
                rootElement = doc.getDocumentElement();
            } else {
                doc = docBuilder.newDocument();
                rootElement = doc.createElement("contatos");
                doc.appendChild(rootElement);
            }

            Element contatoElement = doc.createElement("contato");

            contatoElement.appendChild(criarElemento(doc, "nome" , contato.getNome()));
            contatoElement.appendChild(criarElemento(doc, "nascimento"  , String.valueOf(contato.getNascimento())));
            contatoElement.appendChild(criarElemento(doc, "email", contato.getEmail()));
            contatoElement.appendChild(criarElemento(doc, "telefone"  , contato.getTelefone()));
            
            rootElement.appendChild(contatoElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(data);
            transformer.transform(source, result);


        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }


    }

    private Element criarElemento(Document doc, String nome, String valor) {
        Element node = doc.createElement(nome);
        node.appendChild(doc.createTextNode(valor));
        return node;
    }

    @Override
    public List<Contato> resgatarTodos() {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        List<Contato> contatos = new ArrayList<>();

        try {
            DocumentBuilder docBuilder = domFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(new File(filePath));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("contato");

            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String nome = element.getElementsByTagName("nome").item(0).getTextContent();
                    String email = element.getElementsByTagName("email").item(0).getTextContent();
                    String telefone = element.getElementsByTagName("telefone").item(0).getTextContent();
                    LocalDate nascimento = LocalDate.parse(element.getElementsByTagName("nascimento").item(0).getTextContent());

                    IPersistencia xml = new PersistenciaXML();
                    Contato contato = new Contato(nome, nascimento, email, telefone, xml);

                    contatos.add(contato);
                }
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return contatos;
    }
}
