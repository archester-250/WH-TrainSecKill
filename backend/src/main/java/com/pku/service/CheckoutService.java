package com.pku.service;

import java.math.BigDecimal;
import java.util.List;

public interface CheckoutService {
    BigDecimal checkout(String userId);
}
