/*
  Copyright (c) 2012, 2014, Credit Suisse (Anatole Tresch), Werner Keil and others by the @author tag.

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy of
  the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations under
  the License.
 */
package org.javamoney.moneta.convert;

import java.math.BigDecimal;

import javax.money.convert.ConversionQuery;
import javax.money.convert.ExchangeRate;
import javax.money.convert.RateType;

import org.javamoney.moneta.convert.internal.SuperclassExtracted;
import org.javamoney.moneta.spi.DefaultNumberValue;

/**
 * This class implements an {@link javax.money.convert.ExchangeRateProvider} that provides exchange rate with factor
 * one for identical base/term currencies.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class IdentityRateProvider extends SuperclassExtracted {

    private String newField = "String";

	/**
     * Constructor, also loads initial data.
     */
    public IdentityRateProvider() {
        super(CONTEXT_RENAMED);
    }

	/**
	 * Check if this provider can provide a rate, which is only the case if base and term are equal.
	 *
	 * @param conversionQuery the required {@link ConversionQuery}, not {@code null}
	 * @return true, if the contained base and term currencies are known to this provider.
	 */
	@Override
	public boolean isAvailable(ConversionQuery conversionQuery) {
	    return conversionQuery.getBaseCurrency().getCurrencyCode()
	            .equals(conversionQuery.getCurrency().getCurrencyCode());
	}

	@Override
	public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
	    if (conversionQuery.getBaseCurrency().getCurrencyCode().equals(conversionQuery.getCurrency().getCurrencyCode())) {
	        ExchangeRateBuilder builder = new ExchangeRateBuilder(getContext().getProviderName(), RateType.OTHER)
	                .setBase(conversionQuery.getBaseCurrency());
	        builder.setTerm(conversionQuery.getCurrency());
	        builder.setFactor(DefaultNumberValue.of(BigDecimal.ONE));
	        return builder.build();
	    }
	    return null;
	}

	@Override
	public ExchangeRate getReversed(ExchangeRate rate) {
	    if (rate.getContext().getProviderName().equals(CONTEXT_RENAMED.getProviderName())) {
	        return new ExchangeRateBuilder(rate.getContext()).setTerm(rate.getBaseCurrency())
	                .setBase(rate.getCurrency()).setFactor(new DefaultNumberValue(BigDecimal.ONE)).build();
	    }
	    return null;
	}

	public String getNewField() {
		return newField;
	}

	public void setNewField(String newField) {
		this.newField = newField;
	}

}