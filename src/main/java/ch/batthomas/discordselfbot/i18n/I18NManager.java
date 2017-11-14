package ch.batthomas.discordselfbot.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author batthomas
 */
public class I18NManager {

    private ResourceBundle bundle;

    public I18NManager(String resource) {
        bundle = ResourceBundle.getBundle("ch/batthomas/discordselfbot/config/" + resource, Locale.ENGLISH);
    }

    public String getMessage(String path) {
        return bundle.getString(path);
    }

    public void changeLocale(Locale locale) {
        if (locale != null) {
            bundle = ResourceBundle.getBundle(bundle.getBaseBundleName(), locale);
        } else {
            bundle = ResourceBundle.getBundle(bundle.getBaseBundleName(), Locale.ENGLISH);
        }
    }

    public void changeBundle(String resource) {
        bundle = ResourceBundle.getBundle(resource, bundle.getLocale());
    }

    public ResourceBundle getResourceBundle() {
        return bundle;
    }

    public Locale getLocale() {
        return bundle.getLocale();
    }

}
