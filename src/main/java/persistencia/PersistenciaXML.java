package persistencia;

import contatos.Contato;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PersistenciaXML implements IPersistencia{
    @Override
    public void salvar(Contato contato) {
        File data = new File("data.xml");

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

            contatoElement.appendChild(criarElemento(doc, "name" , contato.getNome()));
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
        return null;
    }
}
