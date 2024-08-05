package fido.uz.mohir_dev_jpa.repository;

import fido.uz.mohir_dev_jpa.entity.PostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDataRepository extends JpaRepository<PostData, Long> {
}
