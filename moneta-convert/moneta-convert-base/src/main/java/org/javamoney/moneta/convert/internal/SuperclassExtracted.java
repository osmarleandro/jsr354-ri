package org.javamoney.moneta.convert.internal;

import javax.money.convert.ProviderContext;
import javax.money.convert.ProviderContextBuilder;
import javax.money.convert.RateType;

import org.javamoney.moneta.spi.AbstractRateProvider;

public abstract class SuperclassExtracted extends AbstractRateProvider {

	/**
	 * The {@link javax.money.convert.ConversionContext} of this provider.
	 */
	protected static final ProviderContext CONTEXT_RENAMED = ProviderContextBuilder.of("IDENT", RateType.OTHER).set("providerDescription", "Identitiy Provider").build();
	
	private String newField = "String";

	public SuperclassExtracted(ProviderContext providerContext) {
		super(providerContext);
	}

	public String getNewField() {
		return newField;
	}

	public void setNewField(String newField) {
		this.newField = newField;
	}

}