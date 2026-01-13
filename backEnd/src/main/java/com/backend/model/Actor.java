package com.backend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Actor {
    @Id
    private String name ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return name.equals(actor.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
