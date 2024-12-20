package co.car.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import co.car.dto.CarDTO;
import co.car.entity.Car;
import co.car.entity.FuelType;
import co.car.error.BusinessException;
import co.car.service.CarService;
import co.car.util.CarDtoToCarConverter;
import co.car.util.CarToCarDtoConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/car")
public class CarController {

	private final CarService carService;
	private final Faker faker;
	private final CarDtoToCarConverter carDtoToCarConverter;
	private final CarToCarDtoConverter carToCarDtoConverter;


	@PostMapping("/add-car")
	public ResponseEntity<Car> addCar(@Valid @RequestBody CarDTO c) {
		c.setName(faker.vehicle().model());
		c.setColor(faker.color().name());
		Car saveCar = new Car();
		try {

			saveCar = carService.addCar(carDtoToCarConverter.convert(c));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return new ResponseEntity<>(saveCar, HttpStatus.OK);
	}

	@PutMapping("/update-car/{cid}")
	public ResponseEntity<Car> updateCar(@Valid @RequestBody CarDTO c, @PathVariable long cid) {
		Car updateCar = new Car();
		try {
			updateCar = carService.updateCar(carDtoToCarConverter.convert(c), cid);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return new ResponseEntity<>(updateCar, HttpStatus.OK);
	}

	@DeleteMapping("/delete-car/{cid}")
	public ResponseEntity<Boolean> deleteCar(@PathVariable long cid) {
		boolean b = false;
		try {
			b = carService.deleteCar(cid);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		return new ResponseEntity<>(b, HttpStatus.OK);
	}

	@GetMapping("/get-cars/{year}")
	public ResponseEntity<Page<Car>> getAllCars(@RequestParam(required = false) String name,
			@RequestParam(required = false) String model, @PathVariable(required = false) Optional<Integer> year, Pageable pageable) {
		 Integer y = Optional.ofNullable(year.get()).orElse(1900);
		 String m = Optional.ofNullable(model).orElse("");
		 String n = Optional.ofNullable(name).orElse("");
		return new ResponseEntity<>(carService.cars(n, m, y, pageable), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<CarDTO>> searchCar(@RequestParam(required = false) String name,
			@RequestParam(required = false) String model, @RequestParam(required = false) int year,
			@RequestParam(required = false) String color, @RequestParam(required = false) String fuel_type,
			Pageable pageable) {
		Page<Car> car = carService.searchCar(name, model, year, color, fuel_type, pageable);
		Page<CarDTO> pageDto = car.map(carToCarDtoConverter::convert);
		return new ResponseEntity<>(pageDto, HttpStatus.OK);
	}

	@PostMapping("/upload-data")
	public ResponseEntity<String> uploadQuestion(@RequestParam("file") MultipartFile file) throws IOException {

		List<Car> v = new ArrayList<>();
		InputStream input_stream = file.getInputStream();
		CSVFormat csvFormat = CSVFormat.Builder.create(CSVFormat.DEFAULT).setSkipHeaderRecord(true)
				.setIgnoreHeaderCase(true).setTrim(true).build();
		try (BufferedReader bReader = new BufferedReader(new InputStreamReader(input_stream, "UTF-8"));
				CSVParser csvParser = new CSVParser(bReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord rec : csvRecords) {
				Car vm = new Car();
				vm.setName(rec.get("name"));
				vm.setYear(Integer.parseInt(rec.get("year")));
				vm.setPrice(Float.parseFloat(rec.get("present_price")));
				vm.setColor(faker.color().name());
				vm.setTrasmission(rec.get("transmission"));
				vm.setModel(faker.vehicle().model());
				log.info("{}", rec.get("fuel_type").toUpperCase() + " " + FuelType.DIESEL.name().toUpperCase());
				if (rec.get("fuel_type").toUpperCase().equals(FuelType.DIESEL.name())) {
					vm.setFuelType(FuelType.DIESEL);
				} else if (rec.get("fuel_type").toUpperCase().equals(FuelType.PETROL.name())) {
					vm.setFuelType(FuelType.PETROL);
				} else if (rec.get("fuel_type").toUpperCase().equals(FuelType.CNG.name())) {
					vm.setFuelType(FuelType.CNG);
				} else if (rec.get("fuel_type").toUpperCase().equals(FuelType.HYBRID.name())) {
					vm.setFuelType(FuelType.HYBRID);
				}

				v.add(vm);
			}
		} catch (IOException e) {
			throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
		}

		Optional<Boolean> saveQuestions = carService.createVehicleModel(v);

		return ResponseEntity.ok("uploaded = " + saveQuestions.get());
	}
}
