package com.duyen.inheritancemapping;

import com.duyen.inheritancemapping.entities.singletable.Foundation;
import com.duyen.inheritancemapping.entities.singletable.SheetMask;
import com.duyen.inheritancemapping.repos.BeautyProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SingleTableTest {

    @Autowired
    BeautyProductRepository beautyProductRepository;

    @Test
    public void testCreateSheetMask() {
        SheetMask sheetMask = new SheetMask();
        sheetMask.setName("Golden Mask");
        sheetMask.setPrice(3d);
        sheetMask.setMaterial("foil");

        beautyProductRepository.save(sheetMask);
    }

    @Test
    public void testCreateFoundation() {
        Foundation foundation = new Foundation();
        foundation.setName("Die this way :p ");
        foundation.setPrice(40d);
        foundation.setShade("3000");

        beautyProductRepository.save(foundation);
    }

}
