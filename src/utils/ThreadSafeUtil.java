package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ThreadSafeUtil {
    private static final ThreadLocal MESSAGE_DIGESTS                                     = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 try {
                                                                                                     return MessageDigest
                                                                                                             .getInstance("SHA-1");
                                                                                                 } catch (NoSuchAlgorithmException ex) {
                                                                                                     throw new AssertionError(
                                                                                                             "Could not initialize MessageDigest");
                                                                                                 }
                                                                                             }
                                                                                         };

    private static final ThreadLocal MESSAGE_DIGESTS_MD5                                 = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 try {
                                                                                                     return MessageDigest
                                                                                                             .getInstance("MD5");
                                                                                                 } catch (NoSuchAlgorithmException ex) {
                                                                                                     throw new AssertionError(
                                                                                                             "Could not initialize MessageDigest");
                                                                                                 }
                                                                                             }
                                                                                         };
    /**
     * @deprecated
     */
    private static final ThreadLocal MESSAGE_DIGESTS_SHA256                              = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 try {
                                                                                                     return MessageDigest
                                                                                                             .getInstance("SHA-256");
                                                                                                 } catch (NoSuchAlgorithmException ex) {
                                                                                                     throw new AssertionError(
                                                                                                             "Could not initialize MessageDigest SHA-256");
                                                                                                 }
                                                                                             }
                                                                                         };

    private static final ThreadLocal DATE_TIME_W3C_FORMAT                                = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 return new SimpleDateFormat(
                                                                                                         "yyyy-MM-dd'T'HH:mm:ss'Z'");
                                                                                             }
                                                                                         };
    private static final ThreadLocal DATE_TIME_FORMAT_WITH_MILLISECOND                   = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 return new SimpleDateFormat(
                                                                                                         "dd/MM/yyyy HH:mm:ss.SSS");
                                                                                             }
                                                                                         };
    private static final ThreadLocal DATE_TIME_12_HRS_FORMAT                             = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 return new SimpleDateFormat(
                                                                                                         "dd/MM/yyyy hh:mm:ss a");
                                                                                             }
                                                                                         };
    private static final ThreadLocal DATE_TIME_12_HRS_INT_FORMAT                         = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 return new SimpleDateFormat(
                                                                                                         "MM/dd/yyyy hh:mm:ss a");
                                                                                             }
                                                                                         };
    private static final ThreadLocal DATE_TIME_12_HRS_WITHOUT_SECONDS_FORMAT             = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 return new SimpleDateFormat(
                                                                                                         "dd/MM/yyyy hh:mm a");
                                                                                             }
                                                                                         };
    private static final ThreadLocal DATE_TIME_12_HRS_WITHOUT_SECONDS_WITH_HYPHEN_FORMAT = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 return new SimpleDateFormat(
                                                                                                         "dd-MM-yyyy hh:mm a");
                                                                                             }
                                                                                         };
    private static final ThreadLocal DATE_TIME_12_HRS_WITHOUT_SECONDS_WITH_DOT_FORMAT;
    private static final ThreadLocal STANDARD_DATE_FORMAT;
    private static final ThreadLocal yyyyMMdd_DATE_FORMAT;
    private static final ThreadLocal yyyyMMddHHmmss_DATE_FORMAT;
    private static final ThreadLocal dd_MMM_DATE_FORMAT;
    private static final ThreadLocal ddMMyyyy_DATE_FORMAT;
    private static final ThreadLocal dd_MM_yyyy_DATE_FORMAT;
    private static final ThreadLocal dd_MMM_EXPORT_DATE_FORMAT;
    private static final ThreadLocal dd_MMM_yy_DATE_FORMAT;
    private static final ThreadLocal dd_MM_yy_DATE_FORMAT;
    private static final ThreadLocal DD_MMMMM_YYYY_DATE_FORMAT;
    private static final ThreadLocal DD_MMMMM_YY_DATE_FORMAT;
    private static final ThreadLocal DD_MMMMM_DATE_FORMAT;
    private static final ThreadLocal MMMMM_YYYY_DATE_FORMAT;
    private static final ThreadLocal DD_EEE_DATE_FORMAT;
    private static final ThreadLocal EEE_AT_HMMA_FORMAT;
    private static final ThreadLocal MMMMM_d_AT_HMMA_FORMAT;
    private static final ThreadLocal MMMMM_d_FORMAT;
    private static final ThreadLocal MMMMM_d_yyyy_AT_HMMA_FORMAT;
    private static final ThreadLocal MMMMM_d_yyyy_FORMAT;
    private static final ThreadLocal MMMMM_d_yyyy_EEEEE_FORMAT;
    private static final ThreadLocal dd_MMM_yy_HHMMA_FORMAT;
    private static final ThreadLocal dd_MM_yy_24hrs_FORMAT;
    private static final ThreadLocal STANDARD_DATE_TIME_FORMAT;
    private static final ThreadLocal STANDARD_TIME_FORMAT;
    private static final ThreadLocal DATE_FORMAT;
    private static final ThreadLocal SIMPLE_DATE_FORMAT;
    private static final ThreadLocal SIMPLE_DATE_TIME_FORMAT;
    private static final ThreadLocal RAIL_ARRIVAL_DATE_TIME_FORMAT;
    private static final ThreadLocal DISPLAY_DATE_TIME_FORMAT                            = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 return new SimpleDateFormat(
                                                                                                         "dd MMM, yyyy 'at' hh:mm a");
                                                                                             }
                                                                                         };
    private static final ThreadLocal LONG_DISPLAY_DATE_FORMAT;
    private static final ThreadLocal LONG_DISPLAY_DATE_WITHOUT_COMMA_FORMAT;
    private static final ThreadLocal SHORT_DISPLAY_DAY_FORMAT;
    private static final ThreadLocal LONG_DISPLAY_DAY_FORMAT;
    private static final ThreadLocal SHORT_DISPLAY_MONTH_FORMAT;
    private static final ThreadLocal LONG_DISPLAY_MONTH_FORMAT;
    private static final ThreadLocal LONG_MONTH_DISPLAY_DATE_FORMAT;
    private static final ThreadLocal SHORT_DISPLAY_DATE_FORMAT;
    private static final ThreadLocal SHORT_DISPLAY_DATE_FORMAT_WITH_DASH;
    private static final ThreadLocal YUI_DATE_FORMAT;
    private static final ThreadLocal TIME_12_HRS_FORMAT;
    private static final ThreadLocal TIME_12_HRS_WITHOUT_SECONDS_FORMAT;
    private static final ThreadLocal TIME_12_HRS_WITHOUT_SECONDS_WITHOUT_SPACE_FORMAT;
    private static final ThreadLocal TIME_12_HRS_WITHOUT_SECONDS_WITH_EXTRASPACE_FORMAT;
    private static final ThreadLocal TIME_24_HRS_FORMAT;
    private static final ThreadLocal TIMESTAMP_WITHOUT_SPACE_FORMAT;
    private static final ThreadLocal ONLY_TIME_24_HRS_FORMAT;
    private static final ThreadLocal ONLY_TIME_24_HRS_FORMAT_SMALL;
    private static final ThreadLocal INCOMING_SMS_DATETIME_FORMAT;
    private static final ThreadLocal DECIMAL_FORMAT;
    static {
        DATE_TIME_12_HRS_WITHOUT_SECONDS_WITH_DOT_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd.MM.yyyy hh:mm a");
            }

        };
        TIME_24_HRS_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd/MM/yyyy HH:mm");
            }
        };
        STANDARD_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };
        yyyyMMdd_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("yyyyMMdd");
            }

        };
        yyyyMMddHHmmss_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("yyyyMMddHHmmss");
            }

        };
        dd_MMM_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd MMM");
            }

        };
        ddMMyyyy_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("ddMMyyyy");
            }

        };
        dd_MM_yyyy_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd/MM/yyyy");
            }

        };
        dd_MMM_EXPORT_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("ddMMM");
            }

        };
        EEE_AT_HMMA_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("EEE h:mm a");
            }
        };
        MMMMM_d_AT_HMMA_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMMMM d 'at' h:mm a");
            }
        };
        MMMMM_d_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMMMM d");
            }
        };
        MMMMM_d_yyyy_AT_HMMA_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMMMM d, yyyy 'at' h:mm a");
            }
        };
        dd_MMM_yy_HHMMA_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd MMM yy hh:mm a");
            }
        };
        dd_MM_yy_24hrs_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            }
        };
        MMMMM_d_yyyy_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMMMM d, yyyy");
            }

        };
        MMMMM_d_yyyy_EEEEE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMMMM d, yyyy '('EEEEE')'");
            }

        };
        dd_MMM_yy_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd MMM yy");
            }

        };
        dd_MM_yy_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd/MM/yy");
            }

        };
        MMMMM_YYYY_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMMMM yyyy");
            }

        };
        DD_EEE_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd EEE");
            }

        };
        STANDARD_DATE_TIME_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        };
        STANDARD_TIME_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("HH:mm:ss");
            }

        };
        TRAVELGURU_CANCELLATION_DATE_TIME_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            }

        };
        DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd/MM/yyyy");
            }

        };
        SIMPLE_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd-MM-yyyy");
            }

        };
        SIMPLE_DATE_TIME_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            }

        };
        RAIL_ARRIVAL_DATE_TIME_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            }
        };
        LONG_DISPLAY_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("EEE, dd MMM, yyyy");
            }
        };
        LONG_DISPLAY_DATE_WITHOUT_COMMA_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("EEE, dd MMM yyyy");
            }
        };
        SHORT_DISPLAY_DAY_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("EEE");
            }
        };
        LONG_DISPLAY_DAY_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("EEEEE");
            }
        };
        SHORT_DISPLAY_MONTH_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMM");
            }
        };
        LONG_DISPLAY_MONTH_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMMMM");
            }
        };
        SHORT_DISPLAY_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd MMM yyyy");
            }
        };
        SHORT_DISPLAY_DATE_FORMAT_WITH_DASH = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd-MMM-yyyy");
            }
        };
        LONG_MONTH_DISPLAY_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMMMM dd, yyyy");
            }
        };
        DD_MMMMM_YYYY_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd MMMMM yyyy");
            }
        };
        DD_MMMMM_YY_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd MMMMM, ''yy");
            }
        };
        DD_MMMMM_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("dd MMMMM");
            }
        };
        YUI_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MM/dd/yyyy");
            }
        };
        MM_DD_YYYY_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MMddyyyy");
            }
        };
        MM_DD_YYYY_WITH_SLASH_DATE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MM/DD/YYYY");
            }
        };
        TIME_12_HRS_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("hh:mm:ss a");
            }
        };
        TIME_12_HRS_WITHOUT_SECONDS_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("hh:mm a");
            }
        };
        TIME_12_HRS_WITHOUT_SECONDS_WITHOUT_SPACE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("hh:mma");
            }
        };
        TIME_12_HRS_WITHOUT_SECONDS_WITH_EXTRASPACE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("h : mm a");
            }
        };
        ONLY_TIME_24_HRS_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("HH:mm");
            }
        };
        ONLY_TIME_24_HRS_FORMAT_SMALL = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("HHmm");
            }
        };
        TIMESTAMP_WITHOUT_SPACE_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("ddMMyyyyHHmmss");
            }
        };
        DECIMAL_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new DecimalFormat("##########0.00");
            }

        };
        DECIMAL_FORMAT_DOUBLE_DECIMAL = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new DecimalFormat("##########0.00");
            }

        };
        DECIMAL_FORMAT_SINGLE_DECIMAL = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new DecimalFormat("##########0.0");
            }
        };
        DECIMAL_FORMAT_TRIPLE_DECIMAL = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new DecimalFormat("##########0.000");
            }
        };
        INCOMING_SMS_DATETIME_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("MM/dd/yy hh:mm:ss a");
            }
        };
        EEE_DD_MMM_YYYY_DATETIME_FORMAT = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
            }
        };
        DECIMAL_FORMAT_NODECIMAL = new ThreadLocal() {
            protected synchronized Object initialValue() {
                return new DecimalFormat("##########0");
            }
        };
        NUMBER_FORMAT_MAX_NO_DECIMAL = new ThreadLocal() {
            protected synchronized Object initialValue() {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMinimumFractionDigits(0);
                numberFormat.setMaximumFractionDigits(0);
                return numberFormat;
            }
        };
        NUMBER_FORMAT_MAX_ONE_DECIMAL = new ThreadLocal() {
            protected synchronized Object initialValue() {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMinimumFractionDigits(0);
                numberFormat.setMaximumFractionDigits(1);
                return numberFormat;
            }
        };
        NUMBER_FORMAT_MAX_ONE_DECIMAL_NO_GROUPING = new ThreadLocal() {
            protected synchronized Object initialValue() {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMinimumFractionDigits(0);
                numberFormat.setMaximumFractionDigits(1);
                numberFormat.setGroupingUsed(false);
                return numberFormat;
            }

        };
        NUMBER_FORMAT_TWO_DECIMAL = new ThreadLocal() {
            protected synchronized Object initialValue() {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMinimumFractionDigits(2);
                numberFormat.setMaximumFractionDigits(2);
                return numberFormat;
            }
        };
        NUMBER_FORMAT_TWO_DECIMAL_NO_GROUPING = new ThreadLocal() {
            protected synchronized Object initialValue() {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMinimumFractionDigits(2);
                numberFormat.setMaximumFractionDigits(2);
                numberFormat.setGroupingUsed(false);
                return numberFormat;
            }
        };
        NUMBER_FORMAT_MAX_TWO_DECIMAL = new ThreadLocal() {
            protected synchronized Object initialValue() {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMinimumFractionDigits(0);
                numberFormat.setMaximumFractionDigits(2);
                return numberFormat;
            }
        };
    }

    private static final ThreadLocal DECIMAL_FORMAT_SINGLE_DECIMAL;
    private static final ThreadLocal DECIMAL_FORMAT_DOUBLE_DECIMAL;
    private static final ThreadLocal DECIMAL_FORMAT_TRIPLE_DECIMAL;
    private static final ThreadLocal DECIMAL_FORMAT_NODECIMAL;
    private static final ThreadLocal NUMBER_FORMAT_MAX_ONE_DECIMAL_NO_GROUPING;
    private static final ThreadLocal NUMBER_FORMAT_MAX_NO_DECIMAL;
    private static final ThreadLocal NUMBER_FORMAT_MAX_ONE_DECIMAL;
    private static final ThreadLocal NUMBER_FORMAT_MAX_TWO_DECIMAL_NO_GROUPING           = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 NumberFormat numberFormat = NumberFormat
                                                                                                         .getInstance();
                                                                                                 numberFormat
                                                                                                         .setMinimumFractionDigits(0);
                                                                                                 numberFormat
                                                                                                         .setMaximumFractionDigits(2);
                                                                                                 numberFormat
                                                                                                         .setGroupingUsed(false);
                                                                                                 return numberFormat;
                                                                                             }
                                                                                         };
    private static final ThreadLocal NUMBER_FORMAT_MAX_TWO_DECIMAL;
    private static final ThreadLocal NUMBER_FORMAT_MAX_THREE_DECIMAL                     = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 NumberFormat numberFormat = NumberFormat
                                                                                                         .getInstance();
                                                                                                 numberFormat
                                                                                                         .setMinimumFractionDigits(0);
                                                                                                 numberFormat
                                                                                                         .setMaximumFractionDigits(3);
                                                                                                 return numberFormat;
                                                                                             }
                                                                                         };
    private static final ThreadLocal NUMBER_FORMAT_MAX_THREE_DECIMAL_NO_GROUPING         = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 NumberFormat numberFormat = NumberFormat
                                                                                                         .getInstance();
                                                                                                 numberFormat
                                                                                                         .setMinimumFractionDigits(0);
                                                                                                 numberFormat
                                                                                                         .setMaximumFractionDigits(3);
                                                                                                 numberFormat
                                                                                                         .setGroupingUsed(false);
                                                                                                 return numberFormat;
                                                                                             }
                                                                                         };
    private static final ThreadLocal NUMBER_FORMAT_TWO_DECIMAL;
    private static final ThreadLocal NUMBER_FORMAT_TWO_DECIMAL_NO_GROUPING;
    private static final ThreadLocal NUMBER_FORMAT_LAT_LNG                               = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 NumberFormat numberFormat = NumberFormat
                                                                                                         .getInstance();
                                                                                                 numberFormat
                                                                                                         .setMinimumFractionDigits(1);
                                                                                                 numberFormat
                                                                                                         .setMaximumFractionDigits(6);
                                                                                                 numberFormat
                                                                                                         .setGroupingUsed(false);
                                                                                                 return numberFormat;
                                                                                             }
                                                                                         };
    private static final ThreadLocal EEE_DD_MMM_YYYY_DATETIME_FORMAT;
    private static final ThreadLocal MM_DD_YYYY_DATE_FORMAT;
    private static final ThreadLocal MM_DD_YYYY_WITH_SLASH_DATE_FORMAT;
    private static final ThreadLocal TRAVELGURU_CANCELLATION_DATE_TIME_FORMAT;
    private static final ThreadLocal ddMMMyy_DATE_FORMAT                                 = new ThreadLocal() {
                                                                                             protected synchronized Object initialValue() {
                                                                                                 return new SimpleDateFormat(
                                                                                                         "ddMMMyy");
                                                                                             }
                                                                                         };

    protected ThreadSafeUtil() {
    }

    private static DateFormat setTimeZone(DateFormat format, boolean useUserTimeZone, boolean useViewerTimeZone) {
        return format;
    }

    public static MessageDigest getMessageDigest() {
        return (MessageDigest) MESSAGE_DIGESTS.get();
    }

    public static MessageDigest getMessageDigestMD5() {
        return (MessageDigest) MESSAGE_DIGESTS_MD5.get();
    }

    public static DateFormat getDateTimeW3CFormat() {
        DateFormat format = (DateFormat) DATE_TIME_W3C_FORMAT.get();

        return format;
    }

    public static DateFormat getDateTimeFormatWithMillisecond(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DATE_TIME_FORMAT_WITH_MILLISECOND.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDateTime12HrsFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DATE_TIME_12_HRS_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDateTime12HrsIntFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DATE_TIME_12_HRS_INT_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDateTime12HrsWithoudSecondsFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DATE_TIME_12_HRS_WITHOUT_SECONDS_FORMAT.get(), useUserTimeZone,
                useViewerTimeZone);
    }

    public static DateFormat getDateTime12HrsWithoutSecondsHyphenFormat(boolean useUserTimeZone,
            boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DATE_TIME_12_HRS_WITHOUT_SECONDS_WITH_HYPHEN_FORMAT.get(), useUserTimeZone,
                useViewerTimeZone);
    }

    public static DateFormat getDateTime12HrsWithoutSecondsDotFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DATE_TIME_12_HRS_WITHOUT_SECONDS_WITH_DOT_FORMAT.get(), useUserTimeZone,
                useViewerTimeZone);
    }

    public static DateFormat getTime24HrsFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) TIME_24_HRS_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getSimpleDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) SIMPLE_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getSimpleDateTimeFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) SIMPLE_DATE_TIME_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getRailArrivalDateTimeFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) RAIL_ARRIVAL_DATE_TIME_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDisplayDateTimeFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DISPLAY_DATE_TIME_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getLongDisplayDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) LONG_DISPLAY_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getLongDisplayWithoutCommaDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) LONG_DISPLAY_DATE_WITHOUT_COMMA_FORMAT.get(), useUserTimeZone,
                useViewerTimeZone);
    }

    public static DateFormat getShortDisplayDayFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) SHORT_DISPLAY_DAY_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getLongDisplayDayFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) LONG_DISPLAY_DAY_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getLongMonthDisplayDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) LONG_MONTH_DISPLAY_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getShortDisplayMonthFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) SHORT_DISPLAY_MONTH_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getLongDisplayMonthFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) LONG_DISPLAY_MONTH_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getShortDisplayDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) SHORT_DISPLAY_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getShortDisplayDateFormatWithDash(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) SHORT_DISPLAY_DATE_FORMAT_WITH_DASH.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getYUIDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) YUI_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getStandardDateTimeFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) STANDARD_DATE_TIME_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getStandardTimeFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) STANDARD_TIME_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getStandardDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) STANDARD_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getyyyyMMddDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) yyyyMMdd_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getyyyyMMddHHmmssDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) yyyyMMddHHmmss_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getddMMMDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) dd_MMM_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getddMMyyyyDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) ddMMyyyy_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getddMMMyyyyDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) dd_MM_yyyy_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getddMMMExportDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) dd_MMM_EXPORT_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getddMMMyyDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) dd_MMM_yy_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getddMMMyyDateFormat1(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) ddMMMyy_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getddMMyyDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) dd_MM_yy_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getMMddyyyyDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) MM_DD_YYYY_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getMMddyyyyWithSlashDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) MM_DD_YYYY_WITH_SLASH_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDDMMMMMyyyyDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DD_MMMMM_YYYY_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDDMMMMMyyDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DD_MMMMM_YY_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDDMMMMMDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DD_MMMMM_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getMMMMMYYYYDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) MMMMM_YYYY_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDDEEEDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) DD_EEE_DATE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getEEEATHMMADateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) EEE_AT_HMMA_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getMMMMMdATHMMADateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) MMMMM_d_AT_HMMA_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getMMMMMdDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) MMMMM_d_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getMMMMMdyyyyDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) MMMMM_d_yyyy_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getMMMMMdyyyyEEEEEDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) MMMMM_d_yyyy_EEEEE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getMMMMMdyyyyATHMMADateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) MMMMM_d_yyyy_AT_HMMA_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDDMMMyyHHMMADateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) dd_MMM_yy_HHMMA_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDDMMyy24HrsDateFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) dd_MM_yy_24hrs_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getTime12HrsFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) TIME_12_HRS_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getTime12HrsWithoutSecondsFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) TIME_12_HRS_WITHOUT_SECONDS_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getTime12HrsWithoutSecondsWithoutSpaceFormat(boolean useUserTimeZone,
            boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) TIME_12_HRS_WITHOUT_SECONDS_WITHOUT_SPACE_FORMAT.get(), useUserTimeZone,
                useViewerTimeZone);
    }

    public static DateFormat getTime12HrsWithoutSecondsWithExtraSpaceFormat(boolean useUserTimeZone,
            boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) TIME_12_HRS_WITHOUT_SECONDS_WITH_EXTRASPACE_FORMAT.get(), useUserTimeZone,
                useViewerTimeZone);
    }

    public static DateFormat getOnlyTime24HrsFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) ONLY_TIME_24_HRS_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getOnlyTime24HrsFormatSmall(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) ONLY_TIME_24_HRS_FORMAT_SMALL.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getTimeStampWithoutSpaceFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) TIMESTAMP_WITHOUT_SPACE_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getIncomingSMSDateTimeFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) INCOMING_SMS_DATETIME_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getDayDateTimeFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) EEE_DD_MMM_YYYY_DATETIME_FORMAT.get(), useUserTimeZone, useViewerTimeZone);
    }

    public static DateFormat getTravelguruCancellationDateTimeFormat(boolean useUserTimeZone, boolean useViewerTimeZone) {
        return setTimeZone((DateFormat) TRAVELGURU_CANCELLATION_DATE_TIME_FORMAT.get(), useUserTimeZone,
                useViewerTimeZone);
    }

    public static DecimalFormat getDecimalFormat() {
        return (DecimalFormat) DECIMAL_FORMAT.get();
    }

    public static DecimalFormat getDecimalFormatDoubleDecimal() {
        return (DecimalFormat) DECIMAL_FORMAT_DOUBLE_DECIMAL.get();
    }

    public static DecimalFormat getDecimalFormatSingleDecimal() {
        return (DecimalFormat) DECIMAL_FORMAT_SINGLE_DECIMAL.get();
    }

    public static DecimalFormat getDecimalFormatNoDecimal() {
        return (DecimalFormat) DECIMAL_FORMAT_NODECIMAL.get();
    }

    public static DecimalFormat getDecimalFormatTripleDecimal() {
        return (DecimalFormat) DECIMAL_FORMAT_TRIPLE_DECIMAL.get();
    }

    public static NumberFormat getNumberFormatNoDecimal() {
        return (NumberFormat) NUMBER_FORMAT_MAX_NO_DECIMAL.get();
    }

    public static NumberFormat getNumberFormatMaxOneDecimal() {
        return (NumberFormat) NUMBER_FORMAT_MAX_ONE_DECIMAL.get();
    }

    public static NumberFormat getNumberFormatMaxOneDecimalWithoutGrouping() {
        return (NumberFormat) NUMBER_FORMAT_MAX_ONE_DECIMAL_NO_GROUPING.get();
    }

    public static NumberFormat getNumberFormatTwoDecimal() {
        return (NumberFormat) NUMBER_FORMAT_TWO_DECIMAL.get();
    }

    public static NumberFormat getNumberFormatTwoDecimalWithoutGrouping() {
        return (NumberFormat) NUMBER_FORMAT_TWO_DECIMAL_NO_GROUPING.get();
    }

    public static NumberFormat getNumberFormatMaxTwoDecimal() {
        return (NumberFormat) NUMBER_FORMAT_MAX_TWO_DECIMAL.get();
    }

    public static NumberFormat getNumberFormatMaxTwoDecimalWithoutGrouping() {
        return (NumberFormat) NUMBER_FORMAT_MAX_TWO_DECIMAL_NO_GROUPING.get();
    }

    public static NumberFormat getNumberFormatMaxThreeDecimal() {
        return (NumberFormat) NUMBER_FORMAT_MAX_THREE_DECIMAL.get();
    }

    public static NumberFormat getNumberFormatMaxThreeDecimalWithoutGrouping() {
        return (NumberFormat) NUMBER_FORMAT_MAX_THREE_DECIMAL_NO_GROUPING.get();
    }

    public static NumberFormat getNumberFormatLatLng() {
        return (NumberFormat) NUMBER_FORMAT_LAT_LNG.get();
    }
}
