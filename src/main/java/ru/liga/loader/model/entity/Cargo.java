package ru.liga.loader.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.liga.loader.model.structure.CargoJsonStructure;

import java.util.Arrays;

@Getter
public class Cargo {

    private final char[][] form;
    private final int height;
    private final int width;
    private final int area;
    private final char type;
    @Setter
    private String name;

    public Cargo(
            @NonNull String name,
            @NonNull char[][] form) {
        this.name = name;
        this.form = form;
        this.type = form[0][0];
        height = form.length;
        width = Arrays.stream(form)
                .mapToInt(arr -> arr.length)
                .max()
                .orElse(0);
        area = height * width;
    }

    @JsonCreator
    public Cargo(CargoJsonStructure cargoJsonStructure) {
        this.name = cargoJsonStructure.name();
        this.form = cargoJsonStructure.form();
        this.height = cargoJsonStructure.height();
        this.width = cargoJsonStructure.width();
        this.area = cargoJsonStructure.area();
        this.type = cargoJsonStructure.type();
    }

    /**
     * Возвращает копию формы груза.
     * Этот метод возвращает копию формы груза, чтобы избежать изменения исходной формы.
     *
     * @return копия формы груза
     */

    public char[][] getForm() {
        return Arrays.copyOf(form, form.length);
    }

    /**
     * Возвращает строковое представление груза.
     * Этот метод возвращает строковое представление груза, которое представляет собой строку, содержащую форму груза.
     *
     * @return строковое представление груза
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator())
                .append("Название груза: ")
                .append(name)
                .append(System.lineSeparator())
                .append("Форма: ")
                .append(System.lineSeparator());
        for (char[] arr : form) {
            sb.append(new String(arr));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
