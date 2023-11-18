package cz.fi.muni.pv168.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@NonNull
@Getter
public enum EventDuration {
    M0(0),
    M15(15),
    M30(30),
    M45(45),
    M60(60),
    M120(120),
    M240(240);

    final Integer duration;

    @Override
    public String toString() {
        return String.format("%d minutes", this.duration);
    }
}
