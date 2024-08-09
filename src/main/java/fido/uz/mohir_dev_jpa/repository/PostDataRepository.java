package fido.uz.mohir_dev_jpa.repository;

import fido.uz.mohir_dev_jpa.entity.PostData;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface PostDataRepository extends JpaRepository<PostData, Long> {

}
