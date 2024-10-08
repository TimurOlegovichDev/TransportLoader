package ru.liga.loader.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.initializers.CargoInitializer;
import ru.liga.loader.initializers.TruckInitializer;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.repository.impl.DefaultCrudCargoRepository;
import ru.liga.loader.repository.impl.DefaultCrudTransportRepository;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InitializeService {

    private final TruckInitializer truckInitializer;
    private final CargoInitializer cargoInitializer;
    private final DefaultCrudTransportRepository transportDataRepository;
    private final DefaultCrudCargoRepository cargoDataRepository;

    @Autowired
    public InitializeService(TruckInitializer truckInitializer,
                             CargoInitializer cargoInitializer,
                             DefaultCrudTransportRepository transportDataRepository,
                             DefaultCrudCargoRepository cargoDataRepository) {
        this.truckInitializer = truckInitializer;
        this.cargoInitializer = cargoInitializer;
        this.transportDataRepository = transportDataRepository;
        this.cargoDataRepository = cargoDataRepository;
    }

    /**
     * Инициализирует грузы из json файла.
     * Этот метод инициализирует грузы по заданным формам и добавляет их в главный репозиторий.
     *
     * @param filePath путь к файлу
     */

    public void initializeCargos(String filePath) {
        Map<String, Cargo> cargoMap =
                cargoInitializer.initializeFromJson(filePath);
        cargoDataRepository.addAll(cargoMap);
        log.debug("Добавлено груза: {}", cargoMap.size());
    }

    /**
     * Инициализирует транспортные средства из файла JSON.
     * Этот метод инициализирует транспортные средства из файла JSON
     * и добавляет их в менеджер данных транспортных средств.
     * Также подсчитывает количество грузов в каждом транспортном средстве из файла
     * и заносит данные в журнал
     *
     * @param filePath путь к файлу JSON
     */

    public void initializeTransport(String filePath) {
        Map<Transport, List<Cargo>> transportMap =
                truckInitializer.initializeFromJson(filePath);
        transportDataRepository.addAll(transportMap);
        for (Transport transport : transportMap.keySet()) {
            for (Cargo cargo : transportMap.get(transport)) {
                cargoDataRepository.put(cargo);
            }
        }
        log.debug("Добавлено транспорта из файла: {}", transportMap.size());
    }

    /**
     * Инициализирует транспортные средства по размерам.
     *
     * @param list список размеров
     */

    public void initializeTransport(List<TransportSizeStructure> list) {
        List<Transport> transports =
                truckInitializer.initialize(list);
        transportDataRepository.add(transports);
        log.debug("Добавлено транспорта из полученных размеров: {}", transports.size());
    }
}
