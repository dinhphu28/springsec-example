package tech.dinhphu28.springsec.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.dinhphu28.springsec.Entities.Brand;
import tech.dinhphu28.springsec.Entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByBrandIn(List<Brand> brandList);
}
