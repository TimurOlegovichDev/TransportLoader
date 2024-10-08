package ru.liga.loader.algorithm.util.impl;

import org.junit.jupiter.api.Test;
import ru.liga.loader.model.entity.Cargo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultCargoSorterTest {

    private final DefaultCargoSorter sorter = new DefaultCargoSorter();

    @Test
    void testSort_EmptyList_ReturnsEmptyList() {
        List<Cargo> cargos = new ArrayList<>();
        List<Cargo> sortedCargos = sorter.sort(cargos);
        assertNotNull(sortedCargos);
        assertEquals(0, sortedCargos.size());
    }

    @Test
    void testSort_SingleElementList_ReturnsSameList() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        List<Cargo> cargos = List.of(cargo);
        List<Cargo> sortedCargos = sorter.sort(cargos);
        assertNotNull(sortedCargos);
        assertEquals(1, sortedCargos.size());
        assertEquals(cargo, sortedCargos.get(0));
    }

    @Test
    void testSort_MultipleElementsList_SortedByAreaDescending() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A', 'A'}, {'A', 'A'}}); // area = 4
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}}); // area = 1
        Cargo cargo3 = new Cargo("cargo3", new char[][]{{'C', 'C', 'C'}, {'C', 'C', 'C'}}); // area = 9
        List<Cargo> cargos = List.of(cargo1, cargo2, cargo3);
        List<Cargo> sortedCargos = sorter.sort(cargos);
        assertNotNull(sortedCargos);
        assertEquals(3, sortedCargos.size());
        assertEquals(cargo3, sortedCargos.get(0));
        assertEquals(cargo1, sortedCargos.get(1));
        assertEquals(cargo2, sortedCargos.get(2));
    }

    @Test
    void testSort_MultipleElementsListWithEqualAreas_SortedByAreaDescending() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A', 'A'}, {'A', 'A'}}); // area = 4
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B', 'B'}, {'B', 'B'}}); // area = 4
        Cargo cargo3 = new Cargo("cargo3", new char[][]{{'C'}}); // area = 1
        List<Cargo> cargos = List.of(cargo1, cargo2, cargo3);
        List<Cargo> sortedCargos = sorter.sort(cargos);
        assertNotNull(sortedCargos);
        assertEquals(3, sortedCargos.size());
        assertEquals(cargo1, sortedCargos.get(0));
        assertEquals(cargo2, sortedCargos.get(1));
        assertEquals(cargo3, sortedCargos.get(2));
    }
}