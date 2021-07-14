package com.upload.files.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Product product) {
        em.persist(product);
    }

    public Product findOne(Long proNo) {
        return em.find(Product.class, proNo);
    }

    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class)
                .getResultList();
    }

    public void deleteProduct(Product product) {
        em.remove(em.contains(product) ? product : em.merge(product));
    }

    public List<Product> findByFilter(ListSearch listSearch){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;

        return queryFactory
                .select(product)
                .from(product)
                .where(regionLike(listSearch.getRegion()),
                        seasonLike(listSearch.getSeason()),
                        themeLike(listSearch.getTheme()))
                .fetch();
    }

    private BooleanExpression regionLike(String regionCond) {
        QProduct product = QProduct.product;
        return !StringUtils.hasText(regionCond) ? null : product.region.like(regionCond);
    }

    private BooleanExpression seasonLike(String seasonCond) {
        QProduct product = QProduct.product;
        return !StringUtils.hasText(seasonCond) ? null : product.season.like(seasonCond);
    }

    private BooleanExpression themeLike(String themeCond) {
        QProduct product = QProduct.product;
        return !StringUtils.hasText(themeCond) ? null : product.theme.like(themeCond);
    }

}
