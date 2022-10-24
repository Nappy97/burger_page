package com.nappy.burger.repository.order;

import com.nappy.burger.domain.order.Order;
import com.nappy.burger.domain.order.OrderStatus;
import com.nappy.burger.domain.order.QOrder;
import com.nappy.burger.domain.order.QOrderBurger;
import com.nappy.burger.dto.order.OrderSearchDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    // 생성자 DI를 통해서 JPAQueryFactory(EntityManager) 주입
    public OrderRepositoryCustomImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    // 상품 등록일에 대한 조회 조건 BooleanExpression
    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QOrder.order.regTime.after(dateTime);
    }

    // 상품명 또는 등록자 아이디에 대한 조회 조건 BooleanExpression
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("createdBy", searchBy)) {
            return QOrder.order.createdBy.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("username", searchBy)) {
            return QOrder.order.user.username.like("%" + searchQuery + "%");
        }
        return null;
    }

    // 상품 상태에 대한 조회 조건 BooleanExpression
    private BooleanExpression searchOrderStatusEq(OrderStatus orderStatus) {
        return orderStatus == null ? null : QOrder.order.orderStatus.eq(orderStatus);
    }

    @Override
    public Page<Order> getAdminOrderTotalPage(OrderSearchDto orderSearchDto, Pageable pageable) {
        QOrder order = QOrder.order;
        QOrderBurger orderBurger = QOrderBurger.orderBurger;

        QueryResults<Order> results = jpaQueryFactory
                .selectFrom(QOrder.order)
                .where(regDtsAfter(orderSearchDto.getSearchDateType()),
                        searchOrderStatusEq(orderSearchDto.getSearchOrderStatus()),
                        searchByLike(orderSearchDto.getSearchBy(), orderSearchDto.getSearchQuery()))
                .orderBy(QOrder.order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        // 조회 대상 결과
        List<Order> content = results.getResults();

        // 조회 대상 리스트의 개수
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
