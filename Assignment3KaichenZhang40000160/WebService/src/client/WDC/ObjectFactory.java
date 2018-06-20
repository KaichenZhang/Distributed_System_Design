
package client.WDC;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.WDC package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetBookledFlightCountResponse_QNAME = new QName("http://servers/", "getBookledFlightCountResponse");
    private final static QName _EditRecordResponse_QNAME = new QName("http://servers/", "editRecordResponse");
    private final static QName _BookFlightResponse_QNAME = new QName("http://servers/", "bookFlightResponse");
    private final static QName _TransferReservation_QNAME = new QName("http://servers/", "transferReservation");
    private final static QName _BookFlight_QNAME = new QName("http://servers/", "bookFlight");
    private final static QName _EditRecord_QNAME = new QName("http://servers/", "editRecord");
    private final static QName _GetBookledFlightCount_QNAME = new QName("http://servers/", "getBookledFlightCount");
    private final static QName _TransferReservationResponse_QNAME = new QName("http://servers/", "transferReservationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.WDC
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BookFlightResponse }
     * 
     */
    public BookFlightResponse createBookFlightResponse() {
        return new BookFlightResponse();
    }

    /**
     * Create an instance of {@link TransferReservation }
     * 
     */
    public TransferReservation createTransferReservation() {
        return new TransferReservation();
    }

    /**
     * Create an instance of {@link BookFlight }
     * 
     */
    public BookFlight createBookFlight() {
        return new BookFlight();
    }

    /**
     * Create an instance of {@link EditRecord }
     * 
     */
    public EditRecord createEditRecord() {
        return new EditRecord();
    }

    /**
     * Create an instance of {@link GetBookledFlightCount }
     * 
     */
    public GetBookledFlightCount createGetBookledFlightCount() {
        return new GetBookledFlightCount();
    }

    /**
     * Create an instance of {@link TransferReservationResponse }
     * 
     */
    public TransferReservationResponse createTransferReservationResponse() {
        return new TransferReservationResponse();
    }

    /**
     * Create an instance of {@link GetBookledFlightCountResponse }
     * 
     */
    public GetBookledFlightCountResponse createGetBookledFlightCountResponse() {
        return new GetBookledFlightCountResponse();
    }

    /**
     * Create an instance of {@link EditRecordResponse }
     * 
     */
    public EditRecordResponse createEditRecordResponse() {
        return new EditRecordResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookledFlightCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers/", name = "getBookledFlightCountResponse")
    public JAXBElement<GetBookledFlightCountResponse> createGetBookledFlightCountResponse(GetBookledFlightCountResponse value) {
        return new JAXBElement<GetBookledFlightCountResponse>(_GetBookledFlightCountResponse_QNAME, GetBookledFlightCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers/", name = "editRecordResponse")
    public JAXBElement<EditRecordResponse> createEditRecordResponse(EditRecordResponse value) {
        return new JAXBElement<EditRecordResponse>(_EditRecordResponse_QNAME, EditRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers/", name = "bookFlightResponse")
    public JAXBElement<BookFlightResponse> createBookFlightResponse(BookFlightResponse value) {
        return new JAXBElement<BookFlightResponse>(_BookFlightResponse_QNAME, BookFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers/", name = "transferReservation")
    public JAXBElement<TransferReservation> createTransferReservation(TransferReservation value) {
        return new JAXBElement<TransferReservation>(_TransferReservation_QNAME, TransferReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers/", name = "bookFlight")
    public JAXBElement<BookFlight> createBookFlight(BookFlight value) {
        return new JAXBElement<BookFlight>(_BookFlight_QNAME, BookFlight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers/", name = "editRecord")
    public JAXBElement<EditRecord> createEditRecord(EditRecord value) {
        return new JAXBElement<EditRecord>(_EditRecord_QNAME, EditRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookledFlightCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers/", name = "getBookledFlightCount")
    public JAXBElement<GetBookledFlightCount> createGetBookledFlightCount(GetBookledFlightCount value) {
        return new JAXBElement<GetBookledFlightCount>(_GetBookledFlightCount_QNAME, GetBookledFlightCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers/", name = "transferReservationResponse")
    public JAXBElement<TransferReservationResponse> createTransferReservationResponse(TransferReservationResponse value) {
        return new JAXBElement<TransferReservationResponse>(_TransferReservationResponse_QNAME, TransferReservationResponse.class, null, value);
    }

}
