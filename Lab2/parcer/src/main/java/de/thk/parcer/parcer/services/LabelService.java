package de.thk.parcer.parcer.services;

import de.thk.parcer.parcer.domain.Address;
import de.thk.parcer.parcer.domain.Label;

/**
 * Interface for creation and downloading of PDF labels.
 */
public interface LabelService {
    /**
     * Creates and stores a PDF label with the given {@code recipientAddress} and {@code shipmentNumber}
     * @param recipientAddress the recipientAddress that is supposed to be printed on the label
     * @param shipmentNumber the shipmentNumber that is supposed to be printed on the label
     * @return a Label that wraps the PDF label
     */
    Label createLabel(Address recipientAddress, String shipmentNumber);

    /**
     * Returns a label with the given id
     * @param id the id of the label
     * @return a Label that wraps the PDF label
     * @throws de.thk.parcer.parcer.exceptions.ResourceNotFoundException raised if the label with the given
     * could not be found
     */
    Label getLabelById(long id);
}