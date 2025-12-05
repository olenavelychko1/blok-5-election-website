package com.example.electionbackend.type;

public class OrderBy {

    private String field;
    private Direction direction = Direction.ASC;

    public enum Direction {
        ASC, DESC
    }

    public OrderBy() {
        // default constructor required for Spring binding
    }

    public OrderBy(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
