package com.example.shinhan_spring_study.repository;

import com.example.shinhan_spring_study.entity.Boards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//BoardRepository==BoardDao
//JpaRepository ==CRUD
@Repository
//@Component<@Repository
//JpaRepository 는 entity 를 기반으로 쿼리 생성 및 결과 맵핑
public interface BoardRepository extends JpaRepository<Boards,Integer> {

}
