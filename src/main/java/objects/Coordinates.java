package objects;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "coordinates")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @DecimalMin(value = "-596", inclusive = false)
    @Column(name = "x")
    private long x; //Значение поля должно быть больше -596

    @Column(name = "y")
    private int y;

    // ------------ добавленные

    @Column(name = "owner_id")
    private long ownerId;

    // ------------

    public Coordinates(long x, int y, long ownerId) {
        this.x = x;
        this.y = y;
        this.ownerId = ownerId;
    }

    public Coordinates(long x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toJson() {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    @Override
    public String toString() {
        return (id + ". x: " + x + ", y: " + y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y &&
                ownerId == that.ownerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, ownerId);
    }
}