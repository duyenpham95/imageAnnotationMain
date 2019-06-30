package com.duyen.inheritancemapping.entities.tableperclass;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Shape {

    @Id
    @TableGenerator(
            name = "id_gen_table",
            table = "id_generator",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_gen_table")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
