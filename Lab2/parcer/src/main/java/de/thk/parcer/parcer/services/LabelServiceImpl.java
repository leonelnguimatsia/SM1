package de.thk.parcer.parcer.services;

import com.itextpdf.text.DocumentException;
import de.thk.parcer.parcer.domain.Address;
import de.thk.parcer.parcer.domain.Label;
import de.thk.parcer.parcer.exceptions.ResourceNotFoundException;
import de.thk.parcer.parcer.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    private final TemplateEngine templateEngine;

    @Autowired
    public LabelServiceImpl(final LabelRepository labelRepository) {
        this.labelRepository = labelRepository;

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    public Label createLabel(Address recipientAddress, String shipmentNumber) {
        Label label = new Label();
        Context context = new Context();
        context.setVariable("recipientAddress", recipientAddress);
        context.setVariable("shipmentNumber", shipmentNumber);
        String html = templateEngine.process("labelTemplate", context);
        ByteArrayOutputStream outputStream;
        try {
            outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.flush();
            label.setBase64EncodedLabel(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
            outputStream.close();
        } catch (DocumentException | IOException e) {
            throw new IllegalStateException("PDF generation failed", e);
        }
        return labelRepository.save(label);
    }

    @Override
    public Label getLabelById(long id) {
        Optional<Label> label = labelRepository.findById(id);
        if (label.isPresent()) {
            return label.get();
        } else {
            throw new ResourceNotFoundException("label with id '" + id + "' not found.'");
        }
    }

}