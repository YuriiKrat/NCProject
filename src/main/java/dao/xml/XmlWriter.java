package dao.xml;

import org.apache.log4j.Logger;

import javax.xml.bind.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.03.17.
 */
public abstract class XmlWriter<T> {

    private static final Logger logger = Logger.getLogger(XmlWriter.class);

    private Marshaller jaxbMarshaller;
    private Unmarshaller jaxbUnmarshaller;
    private File file;

    protected abstract Class<T> getEntityClass();

    public XmlWriter(String filename) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(getEntityClass());

        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        file = new File(filename);
    }

    protected T unmarshall() {
        T element = null;

        if(file.exists()) {
            Source source = new StreamSource(file);
            try {
                logger.info("Attempting to retrieve " + getEntityClass().getName() + " entities from xml");

                JAXBElement<T> root = jaxbUnmarshaller.unmarshal(source, getEntityClass());
                element = root.getValue();
            } catch (JAXBException e) {
                logger.error("Failing to retrieve " + getEntityClass().getName() + " entities from xml!" +
                        e.getMessage());
            }
        }
        return element;
    }

    protected void marshall(T element) {
        try {
            logger.info("Attempting to write " + getEntityClass().getName() + " enities to xml");
            jaxbMarshaller.marshal(element, file);
        } catch (JAXBException e) {
            logger.error("Failing to write " + getEntityClass().getName() + " entities to xml!");
        }
    }
}

