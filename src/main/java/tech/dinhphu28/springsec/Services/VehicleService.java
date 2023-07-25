package tech.dinhphu28.springsec.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.dinhphu28.springsec.Entities.Vehicle;
import tech.dinhphu28.springsec.Repositories.VehicleRepository;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository repo;

    public List<Vehicle> retrieveAll() {
        return repo.findAll();
    }
}
