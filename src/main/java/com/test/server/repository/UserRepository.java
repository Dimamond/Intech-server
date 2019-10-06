package com.test.server.repository;


import com.test.server.entity.Content;
import com.test.server.entity.User;
import com.test.server.entity.UserContent;
import com.test.server.exception.MyServerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class UserRepository {


    @PersistenceContext
    private EntityManager em;

    public User selectUserByLogin(String login) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        Predicate predicate = cb.equal(root.get("login"), login);
        cq.select(root).where(predicate);
        List<User> resultList = em.createQuery(cq).getResultList();
        if(resultList.size() != 1)
            throw new MyServerException(HttpStatus.NOT_FOUND,"User not found");

        return resultList.get(0);
    }



    public Page<Content> selectUserContent(int pageNum, int pageSize, Long userId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Content> cq = cb.createQuery(Content.class);
        Root<UserContent> root = cq.from(UserContent.class);
        Join<UserContent, Content> joinContent = root.join("content", JoinType.INNER);
        Join<UserContent, User> joinUser = root.join("user", JoinType.INNER);

        Predicate predicate = cb.equal(joinUser.get("id"), userId);

        Order orders = cb.asc(root.get("id"));
        cq.select(joinContent).where(predicate).orderBy(orders);

        List<Content> resultList = em.createQuery(cq).setFirstResult(pageNum * pageSize).setMaxResults(pageSize).getResultList();
        Long totalRows = countContent(userId);

        return new PageImpl<>(resultList, PageRequest.of(pageNum, pageSize), totalRows);
    }

    public Long countContent(Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<UserContent> root = cq.from(UserContent.class);
        Join<UserContent, Content> joinContent = root.join("content", JoinType.INNER);
        Join<UserContent, User> joinUser = root.join("user", JoinType.INNER);
        Predicate predicate = cb.equal(joinUser.get("id"), userId);
        cq.select(cb.count(joinContent)).where(predicate);
        return em.createQuery(cq).getSingleResult();
    }


    public UserContent insertUserContent(Long userId, Long contentId) {

        User userFromDB = em.find(User.class, userId);
        Content contentFromDB = em.find(Content.class, contentId);

        if (userFromDB == null)
            throw new MyServerException(HttpStatus.NOT_FOUND,"User not found by id");

        if (contentFromDB == null)
            throw new MyServerException(HttpStatus.NOT_FOUND,"Content not found by id");

        UserContent userContent = new UserContent();
        userContent.setUser(userFromDB);
        userContent.setContent(contentFromDB);

        em.persist(userContent);
        return userContent;
    }

    public void deleteUserContent(Long userId, Long contentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserContent> cq = cb.createQuery(UserContent.class);
        Root<UserContent> root = cq.from(UserContent.class);
        Join<UserContent, Content> joinContent = root.join("content", JoinType.INNER);
        Join<UserContent, User> joinUser = root.join("user", JoinType.INNER);

        Predicate predicate = cb.and(cb.equal(joinContent.get("id"), contentId),
                                     cb.equal(joinUser.get("id"), userId));

        cq.select(root).where(predicate);

        List<UserContent> resultList = em.createQuery(cq).getResultList();

        if(resultList.size() == 0)
            throw new MyServerException(HttpStatus.NOT_FOUND,"User content not found by user_id and content_id");

        UserContent userContent = resultList.get(0);
        em.remove(userContent);

    }
}
