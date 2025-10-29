
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = "sms", name = "config-type")
public @interface ConditionalOnSmsEnabled {
}
