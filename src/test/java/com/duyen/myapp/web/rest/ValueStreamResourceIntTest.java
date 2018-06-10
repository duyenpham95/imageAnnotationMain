package com.duyen.myapp.web.rest;

import com.duyen.myapp.MainApp;

import com.duyen.myapp.domain.ValueStream;
import com.duyen.myapp.repository.ValueStreamRepository;
import com.duyen.myapp.service.ValueStreamService;
import com.duyen.myapp.service.dto.ValueStreamDTO;
import com.duyen.myapp.service.mapper.ValueStreamMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.duyen.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ValueStreamResource REST controller.
 *
 * @see ValueStreamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApp.class)
public class ValueStreamResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ValueStreamRepository valueStreamRepository;

    @Autowired
    private ValueStreamMapper valueStreamMapper;

    @Autowired
    private ValueStreamService valueStreamService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValueStreamMockMvc;

    private ValueStream valueStream;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValueStreamResource valueStreamResource = new ValueStreamResource(valueStreamService);
        this.restValueStreamMockMvc = MockMvcBuilders.standaloneSetup(valueStreamResource)
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
    public static ValueStream createEntity(EntityManager em) {
        ValueStream valueStream = new ValueStream()
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return valueStream;
    }

    @Before
    public void initTest() {
        valueStream = createEntity(em);
    }

    @Test
    @Transactional
    public void createValueStream() throws Exception {
        int databaseSizeBeforeCreate = valueStreamRepository.findAll().size();

        // Create the ValueStream
        ValueStreamDTO valueStreamDTO = valueStreamMapper.toDto(valueStream);
        restValueStreamMockMvc.perform(post("/api/value-streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamDTO)))
            .andExpect(status().isCreated());

        // Validate the ValueStream in the database
        List<ValueStream> valueStreamList = valueStreamRepository.findAll();
        assertThat(valueStreamList).hasSize(databaseSizeBeforeCreate + 1);
        ValueStream testValueStream = valueStreamList.get(valueStreamList.size() - 1);
        assertThat(testValueStream.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testValueStream.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testValueStream.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createValueStreamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valueStreamRepository.findAll().size();

        // Create the ValueStream with an existing ID
        valueStream.setId(1L);
        ValueStreamDTO valueStreamDTO = valueStreamMapper.toDto(valueStream);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValueStreamMockMvc.perform(post("/api/value-streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ValueStream in the database
        List<ValueStream> valueStreamList = valueStreamRepository.findAll();
        assertThat(valueStreamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = valueStreamRepository.findAll().size();
        // set the field null
        valueStream.setName(null);

        // Create the ValueStream, which fails.
        ValueStreamDTO valueStreamDTO = valueStreamMapper.toDto(valueStream);

        restValueStreamMockMvc.perform(post("/api/value-streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamDTO)))
            .andExpect(status().isBadRequest());

        List<ValueStream> valueStreamList = valueStreamRepository.findAll();
        assertThat(valueStreamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValueStreams() throws Exception {
        // Initialize the database
        valueStreamRepository.saveAndFlush(valueStream);

        // Get all the valueStreamList
        restValueStreamMockMvc.perform(get("/api/value-streams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valueStream.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getValueStream() throws Exception {
        // Initialize the database
        valueStreamRepository.saveAndFlush(valueStream);

        // Get the valueStream
        restValueStreamMockMvc.perform(get("/api/value-streams/{id}", valueStream.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valueStream.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingValueStream() throws Exception {
        // Get the valueStream
        restValueStreamMockMvc.perform(get("/api/value-streams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValueStream() throws Exception {
        // Initialize the database
        valueStreamRepository.saveAndFlush(valueStream);
        int databaseSizeBeforeUpdate = valueStreamRepository.findAll().size();

        // Update the valueStream
        ValueStream updatedValueStream = valueStreamRepository.findOne(valueStream.getId());
        // Disconnect from session so that the updates on updatedValueStream are not directly saved in db
        em.detach(updatedValueStream);
        updatedValueStream
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ValueStreamDTO valueStreamDTO = valueStreamMapper.toDto(updatedValueStream);

        restValueStreamMockMvc.perform(put("/api/value-streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamDTO)))
            .andExpect(status().isOk());

        // Validate the ValueStream in the database
        List<ValueStream> valueStreamList = valueStreamRepository.findAll();
        assertThat(valueStreamList).hasSize(databaseSizeBeforeUpdate);
        ValueStream testValueStream = valueStreamList.get(valueStreamList.size() - 1);
        assertThat(testValueStream.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testValueStream.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testValueStream.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingValueStream() throws Exception {
        int databaseSizeBeforeUpdate = valueStreamRepository.findAll().size();

        // Create the ValueStream
        ValueStreamDTO valueStreamDTO = valueStreamMapper.toDto(valueStream);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValueStreamMockMvc.perform(put("/api/value-streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamDTO)))
            .andExpect(status().isCreated());

        // Validate the ValueStream in the database
        List<ValueStream> valueStreamList = valueStreamRepository.findAll();
        assertThat(valueStreamList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValueStream() throws Exception {
        // Initialize the database
        valueStreamRepository.saveAndFlush(valueStream);
        int databaseSizeBeforeDelete = valueStreamRepository.findAll().size();

        // Get the valueStream
        restValueStreamMockMvc.perform(delete("/api/value-streams/{id}", valueStream.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValueStream> valueStreamList = valueStreamRepository.findAll();
        assertThat(valueStreamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValueStream.class);
        ValueStream valueStream1 = new ValueStream();
        valueStream1.setId(1L);
        ValueStream valueStream2 = new ValueStream();
        valueStream2.setId(valueStream1.getId());
        assertThat(valueStream1).isEqualTo(valueStream2);
        valueStream2.setId(2L);
        assertThat(valueStream1).isNotEqualTo(valueStream2);
        valueStream1.setId(null);
        assertThat(valueStream1).isNotEqualTo(valueStream2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValueStreamDTO.class);
        ValueStreamDTO valueStreamDTO1 = new ValueStreamDTO();
        valueStreamDTO1.setId(1L);
        ValueStreamDTO valueStreamDTO2 = new ValueStreamDTO();
        assertThat(valueStreamDTO1).isNotEqualTo(valueStreamDTO2);
        valueStreamDTO2.setId(valueStreamDTO1.getId());
        assertThat(valueStreamDTO1).isEqualTo(valueStreamDTO2);
        valueStreamDTO2.setId(2L);
        assertThat(valueStreamDTO1).isNotEqualTo(valueStreamDTO2);
        valueStreamDTO1.setId(null);
        assertThat(valueStreamDTO1).isNotEqualTo(valueStreamDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(valueStreamMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(valueStreamMapper.fromId(null)).isNull();
    }
}
