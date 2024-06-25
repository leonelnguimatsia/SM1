package de.thk.parcer.parcer.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.Map;

import com.itextpdf.text.pdf.codec.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.thk.parcer.parcer.domain.Address;
import de.thk.parcer.parcer.domain.Label;
import de.thk.parcer.parcer.services.LabelService;

/**
 * Class that handles incoming restful calls, which concern the resource {@code label}
 */
@RestController
public class LabelController {
    private final LabelService labelService;

    @Autowired
    public LabelController(final LabelService labelService) {
        this.labelService = labelService;
    }

    /**
     * returns a binary pdf file containing a label
     *
     * @param id the id of the label
     * @return a binary pdf if existent
     */
    @RequestMapping(value = "/labels/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLabel(@PathVariable long id) {
        final Label label = labelService.getLabelById(id);
        final ByteArrayResource resource = new ByteArrayResource(Base64.decode(label.getBase64EncodedLabel()));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=label.pdf")
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

    /**
     * Creates a label with a given shipment number and recipient address.
     * 
     * Example call with {@code curl}:
     * 
     * <pre class="code">
     *   curl -sSL -D - -H "Content-Type: application/json" -X POST -d '{"shipmentNumber" : "0815", "recipientAddress": {"line1": "Rene Woerzberger", "line2": "Claudiusstrasse 16", "line3": "51503 Roesrath"}}' http://localhost:8080/labels
     * </pre>
     *
     * @param requestBody the request body carrying a {@code shipmentNumber} and an {@code recipientAddress}
     * @return a download link for the label
     */
    @RequestMapping(value = "/labels", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createLabel(@RequestBody Map<String, Object> requestBody) {
        final String shipmentNumber = requestBody.get("shipmentNumber").toString();
        final Address recipientAddress = new Address();
        recipientAddress.setLine1((String)((Map<String,Object>)requestBody.get("recipientAddress")).get("line1"));
        recipientAddress.setLine2((String)((Map<String,Object>)requestBody.get("recipientAddress")).get("line2"));
        recipientAddress.setLine3((String)((Map<String,Object>)requestBody.get("recipientAddress")).get("line3"));
        final Label label = labelService.createLabel(recipientAddress, shipmentNumber);
        return ResponseEntity
                .created(URI.create(
                        linkTo(methodOn(LabelController.class).getLabel(label.getId())).withSelfRel().getHref()))
                .build();

    }

}