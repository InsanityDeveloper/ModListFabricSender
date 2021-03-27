package de.insanitydev.brandingmodlistsender;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class BrandingModListSender implements ModInitializer {

    @Getter
    private static final Identifier brandingModIdentity = new Identifier("brandingmodlist:channel");

    @Override
    public void onInitialize() {

    }
}
