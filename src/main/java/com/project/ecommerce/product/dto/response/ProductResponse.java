package com.project.ecommerce.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private long idProduct;
    public String name;
    public int price;
    public int category_id;
    public int userFinal;

    @Override
    public String toString() {
        return "ProductResponse{" +
                "idProduct=" + idProduct +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category_id=" + category_id +
                ", userFinal=" + userFinal +
                '}';
    }
}
