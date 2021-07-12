package com.upload.files.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

    /*public int update(Product product, Long proNo) {
        return em.createQuery("update Product p set p.price = :price" +
                ", p.proContent = :proContent" +
                ", p.proTitle = :proTitle" +
                ", p.proWriter = :proWriter" +
                ", p.region = :region}" +
                ", p.season = :#{#product.season}" +
                ", p.theme = :#{#product.theme} where p.proNo = :proNo", Product.class).executeUpdate();
    }*/

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

    /*public List<Product> findByProNo(Long proNo) {
        return em.createQuery("select m from product m where m.proNo = :proNo",
                Product.class)
                .setParameter("proNo", proNo)
                .getResultList();
    }*/

    private BooleanExpression regionLike(String regionCond) {
        QProduct product = QProduct.product;
        boolean cond = StringUtils.hasText(regionCond);
        int count = 0;

        if (!cond) {
            return null;
        }

        return product.region.like(regionCond);
    }

    private BooleanExpression seasonLike(String seasonCond) {
        QProduct product = QProduct.product;

        if (!StringUtils.hasText(seasonCond)) {
            return null;
        }

        return product.season.like(seasonCond);
    }

    private BooleanExpression themeLike(String themeCond) {
        QProduct product = QProduct.product;

        if (!StringUtils.hasText(themeCond)) {
            return null;
        }

        return product.theme.like(themeCond);
    }

}
