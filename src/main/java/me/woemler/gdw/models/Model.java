package me.woemler.gdw.models;

import org.springframework.hateoas.Identifiable;

import java.io.Serializable;

/**
 * @author woemler
 */
public interface Model<ID extends Serializable> extends Serializable, Identifiable<ID> {
}
