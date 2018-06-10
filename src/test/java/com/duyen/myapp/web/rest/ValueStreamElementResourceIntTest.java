package com.duyen.myapp.web.rest;

import com.duyen.myapp.MainApp;

import com.duyen.myapp.domain.ValueStreamElement;
import com.duyen.myapp.repository.ValueStreamElementRepository;
import com.duyen.myapp.service.ValueStreamElementService;
import com.duyen.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.duyen.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ValueStreamElementResource REST controller.
 *
 * @see ValueStreamElementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApp.class)
public class ValueStreamElementResourceIntTest {

    private static final String DEFAULT_ELEMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ELEMENT_NAME = "BBBBBBBBBB";

    @Autowired
    private ValueStreamElementRepository valueStreamElementRepository;

    @Autowired
    private ValueStreamElementService valueStreamElementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValueStreamElementMockMvc;

    private ValueStreamElement valueStreamElement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValueStreamElementResource valueStreamElementResource = new ValueStreamElementResource(valueStreamElementService);
        this.restValueStreamElementMockMvc = MockMvcBuilders.standaloneSetup(valueStreamElementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValueStreamElement createEntity(EntityManager em) {
        ValueStreamElement valueStreamElement = new ValueStreamElement()
            .elementName(DEFAULT_ELEMENT_NAME);
        return valueStreamElement;
    }

    @Before
    public void initTest() {
        valueStreamElement = createEntity(em);
    }

    @Test
    @Transactional
    public void createValueStreamElement() throws Exception {
        int databaseSizeBeforeCreate = valueStreamElementRepository.findAll().size();

        // Create the ValueStreamElement
        restValueStreamElementMockMvc.perform(post("/api/value-stream-elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamElement)))
            .andExpect(status().isCreated());

        // Validate the ValueStreamElement in the database
        List<ValueStreamElement> valueStreamElementList = valueStreamElementRepository.findAll();
        assertThat(valueStreamElementList).hasSize(databaseSizeBeforeCreate + 1);
        ValueStreamElement testValueStreamElement = valueStreamElementList.get(valueStreamElementList.size() - 1);
        assertThat(testValueStreamElement.getElementName()).isEqualTo(DEFAULT_ELEMENT_NAME);
    }

    @Test
    @Transactional
    public void createValueStreamElementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valueStreamElementRepository.findAll().size();

        // Create the ValueStreamElement with an existing ID
        valueStreamElement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValueStreamElementMockMvc.perform(post("/api/value-stream-elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamElement)))
            .andExpect(status().isBadRequest());

        // Validate the ValueStreamElement in the database
        List<ValueStreamElement> valueStreamElementList = valueStreamElementRepository.findAll();
        assertThat(valueStreamElementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllValueStreamElements() throws Exception {
        // Initialize the database
        valueStreamElementRepository.saveAndFlush(valueStreamElement);

        // Get all the valueStreamElementList
        restValueStreamElementMockMvc.perform(get("/api/value-stream-elements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valueStreamElement.getId().intValue())))
            .andExpect(jsonPath("$.[*].elementName").value(hasItem(DEFAULT_ELEMENT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getValueStreamElement() throws Exception {
        // Initialize the database
        valueStreamElementRepository.saveAndFlush(valueStreamElement);

        // Get the valueStreamElement
        restValueStreamElementMockMvc.perform(get("/api/value-stream-elements/{id}", valueStreamElement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valueStreamElement.getId().intValue()))
            .andExpect(jsonPath("$.elementName").value(DEFAULT_ELEMENT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingValueStreamElement() throws Exception {
        // Get the valueStreamElement
        restValueStreamElementMockMvc.perform(get("/api/value-stream-elements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValueStreamElement() throws Exception {
        // Initialize the database
        valueStreamElementService.save(valueStreamElement);

        int databaseSizeBeforeUpdate = valueStreamElementRepository.findAll().size();

        // Update the valueStreamElement
        ValueStreamElement updatedValueStreamElement = valueStreamElementRepository.findOne(valueStreamElement.getId());
        // Disconnect from session so that the updates on updatedValueStreamElement are not directly saved in db
        em.detach(updatedValueStreamElement);
        updatedValueStreamElement
            .elementName(UPDATED_ELEMENT_NAME);

        restValueStreamElementMockMvc.perform(put("/api/value-stream-elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValueStreamElement)))
            .andExpect(status().isOk());

        // Validate the ValueStreamElement in the database
        List<ValueStreamElement> valueStreamElementList = valueStreamElementRepository.findAll();
        assertThat(valueStreamElementList).hasSize(databaseSizeBeforeUpdate);
        ValueStreamElement testValueStreamElement = valueStreamElementList.get(valueStreamElementList.size() - 1);
        assertThat(testValueStreamElement.getElementName()).isEqualTo(UPDATED_ELEMENT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingValueStreamElement() throws Exception {
        int databaseSizeBeforeUpdate = valueStreamElementRepository.findAll().size();

        // Create the ValueStreamElement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValueStreamElementMockMvc.perform(put("/api/value-stream-elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamElement)))
            .andExpect(status().isCreated());

        // Validate the ValueStreamElement in the database
        List<ValueStreamElement> valueStreamElementList = valueStreamElementRepository.findAll();
        assertThat(valueStreamElementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValueStreamElement() throws Exception {
        // Initialize the database
        valueStreamElementService.save(valueStreamElement);

        int databaseSizeBeforeDelete = valueStreamElementRepository.findAll().size();

        // Get the valueStreamElement
        restValueStreamElementMockMvc.perform(delete("/api/value-stream-elements/{id}", valueStreamElement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValueStreamElement> valueStreamElementList = valueStreamElementRepository.findAll();
        assertThat(valueStreamElementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValueStreamElement.class);
        ValueStreamElement valueStreamElement1 = new ValueStreamElement();
        valueStreamElement1.setId(1L);
        ValueStreamElement valueStreamElement2 = new ValueStreamElement();
        valueStreamElement2.setId(valueStreamElement1.getId());
        assertThat(valueStreamElement1).isEqualTo(valueStreamElement2);
        valueStreamElement2.setId(2L);
        assertThat(valueStreamElement1).isNotEqualTo(valueStreamElement2);
        valueStreamElement1.setId(null);
        assertThat(valueStreamElement1).isNotEqualTo(valueStreamElement2);
    }
}
