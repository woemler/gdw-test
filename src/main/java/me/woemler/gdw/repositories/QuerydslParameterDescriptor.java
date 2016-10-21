package me.woemler.gdw.repositories;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;

/**
 * @author woemler
 */
public class QuerydslParameterDescriptor {

    private String name;
    private Path<?> path;
    private Class<?> type;
    private Ops operation;

    public QuerydslParameterDescriptor() {
    }

    public QuerydslParameterDescriptor(String name, Path<?> path, Class<?> type, Ops operation) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path<?> getPath() {
        return path;
    }

    public void setPath(Path<?> path) {
        this.path = path;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Ops getOperation() {
        return operation;
    }

    public void setOperation(Ops operation) {
        this.operation = operation;
    }
}
