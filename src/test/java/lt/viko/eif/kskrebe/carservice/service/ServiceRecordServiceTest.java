package lt.viko.eif.kskrebe.carservice.service;

import lt.viko.eif.kskrebe.carservice.dto.ServiceRecordRequest;
import lt.viko.eif.kskrebe.carservice.exception.ResourceNotFoundException;
import lt.viko.eif.kskrebe.carservice.model.ServiceRecord;
import lt.viko.eif.kskrebe.carservice.repository.CarRepository;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.repository.ServiceRecordRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Unit tests for ServiceRecordService.
 * Mockito is used to mock ServiceRecordRepository and CarRepository.
 * Tests verify:
 * - retrieving all service records
 * - retrieving service record by id
 * - creating a service record
 * - throwing an exception when service record is not found
 * The database is not used during unit testing.
 */

@ExtendWith(MockitoExtension.class)
class ServiceRecordServiceTest {

    @Mock
    private ServiceRecordRepository serviceRecordRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private ServiceRecordService serviceRecordService;

    @Test
    void shouldReturnAllServiceRecords() {

        ServiceRecord record1 = new ServiceRecord();
        record1.setId(1L);

        ServiceRecord record2 = new ServiceRecord();
        record2.setId(2L);

        when(serviceRecordRepository.findAll())
                .thenReturn(List.of(record1, record2));

        List<ServiceRecord> records = serviceRecordService.findAll();

        assertEquals(2, records.size());

        verify(serviceRecordRepository).findAll();
    }

    @Test
    void shouldReturnServiceRecordById() {

        ServiceRecord record = new ServiceRecord();
        record.setId(1L);

        when(serviceRecordRepository.findById(1L))
                .thenReturn(Optional.of(record));

        ServiceRecord result = serviceRecordService.findById(1L);

        assertEquals(1L, result.getId());

        verify(serviceRecordRepository).findById(1L);
    }

    @Test
    void shouldCreateServiceRecord() {

        Car car = new Car();
        car.setId(1L);

        ServiceRecordRequest request = new ServiceRecordRequest();
        request.setCarId(1L);
        request.setDescription("Oil change");

        ServiceRecord savedRecord = new ServiceRecord();
        savedRecord.setId(1L);
        savedRecord.setCar(car);
        savedRecord.setDescription("Oil change");

        when(carRepository.findById(1L))
                .thenReturn(Optional.of(car));

        when(serviceRecordRepository.save(any(ServiceRecord.class)))
                .thenReturn(savedRecord);

        ServiceRecord result = serviceRecordService.create(request);

        assertEquals("Oil change", result.getDescription());
        assertEquals(1L, result.getCar().getId());

        verify(carRepository).findById(1L);
        verify(serviceRecordRepository).save(any(ServiceRecord.class));
    }

    @Test
    void shouldThrowExceptionWhenServiceRecordNotFound() {

        when(serviceRecordRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> serviceRecordService.findById(99L));

        verify(serviceRecordRepository).findById(99L);
    }
}