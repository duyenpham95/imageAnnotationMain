package com.duyen.inheritancemapping;

import com.duyen.inheritancemapping.entities.tableperclass.Circle;
import com.duyen.inheritancemapping.entities.tableperclass.Rectangle;
import com.duyen.inheritancemapping.repos.ShapeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TablePerClassTest {

    @Autowired
    ShapeRepository shapeRepository;

    @Test
    public void testCreateRectangle() {
        Rectangle rect = new Rectangle();
        rect.setHeight(2);
        rect.setWidth(2);

        shapeRepository.save(rect);
    }

    @Test
    public void testCreateCircle() {
        Circle circle = new Circle();
        circle.setRadius(5);

        shapeRepository.save(circle);
    }
}
