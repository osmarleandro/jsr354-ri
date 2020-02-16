package org.javamoney.moneta.convert.internal;

import java.math.BigDecimal;

import javax.money.convert.ConversionQuery;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ProviderContext;
import javax.money.convert.ProviderContextBuilder;
import javax.money.convert.RateType;

import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.AbstractRateProvider;
import org.javamoney.moneta.spi.DefaultNumberValue;

public abstract class SuperclassExtracted extends AbstractRateProvider {

	/**
	 * The {@link javax.money.convert.ConversionContext} of this provider.
	 */
	protected static final ProviderContext CONTEXT = ProviderContextBuilder.of("IDENT", RateType.OTHER).set("providerDescription", "Identitiy Provider").build();

	public SuperclassExtracted(ProviderContext providerContext) {
		super(providerContext);
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
	    if (rate.getContext().getProviderName().equals(CONTEXT.getProviderName())) {
	        return new ExchangeRateBuilder(rate.getContext()).setTerm(rate.getBaseCurrency())
	                .setBase(rate.getCurrency()).setFactor(new DefaultNumberValue(BigDecimal.ONE)).build();
	    }
	    return null;
	}

}