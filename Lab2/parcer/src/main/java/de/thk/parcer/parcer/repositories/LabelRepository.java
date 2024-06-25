package de.thk.parcer.parcer.repositories;

import de.thk.parcer.parcer.domain.Label;
import org.springframework.data.repository.CrudRepository;

/**
 * Data access to for persisted labels.
 */
public interface LabelRepository extends CrudRepository<Label, Long> {
}