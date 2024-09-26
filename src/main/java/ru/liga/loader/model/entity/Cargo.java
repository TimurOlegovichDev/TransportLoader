package ru.liga.loader.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

@Getter
public class Cargo {

    private final char[][] form;
    private final int height;
    private final int width;
    private final int weight;
    private final char type;

    @JsonCreator
    public Cargo(@JsonProperty("form") @NonNull char[][] form) {
        validateForm(form);
        this.form = form;
        this.type = form[0][0];
        weight = (int) Math.pow(Character.getNumericValue(type), 2);
        height = form.length;
        width = Arrays.stream(form)
                .mapToInt(arr -> arr.length)
                .max()
                .orElse(0);
    }

    public Cargo(@JsonProperty("form") @NonNull char[][] form,
                 int height,
                 int width,
                 int weight,
                 char type) {
        validateForm(form);
        this.form = form;
        this.height = height;
        this.width = width;
        this.weight = weight;
        this.type = type;
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

    private void validateForm(char[][] form) {
        if (form.length == 0 || form[0].length == 0) {
            throw new IllegalArgumentException("Груз не может быть пустым");
        }
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
        for (char[] arr : form) {
            sb.append(Arrays.toString(arr));
        }
        return sb.toString();
    }
}
