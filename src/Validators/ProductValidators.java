package Validators;


public class ProductValidators {
    public boolean validateCount(Integer count) {
        if (count == null || count <= 0) return false;
        return true;
    }

    public boolean validateStock(Integer stock) {
        if (stock == null || stock < 0) return false;
        return true;
    }

    public boolean validateId(Integer id) {
        if (id == null || id <= 0) return false;
        return true;
    }
}
