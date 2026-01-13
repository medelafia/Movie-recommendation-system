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
public class Genre {
    @Id
    private String name ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return name.equals(genre.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
