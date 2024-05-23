package tobi.service;

import tobi.dao.UserDao;
import tobi.domain.Level;
import tobi.domain.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class DefaultUserLevelUpgradePolicy implements UserLevelUpgradePolicy {
    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 30;

    private final UserDao userDao;

    public DefaultUserLevelUpgradePolicy(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        return switch (currentLevel) {
            case BASIC -> user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER;
            case SILVER -> user.getRecommend() >= MIN_RECOMMEND_COUNT_FOR_GOLD;
            case GOLD -> false;
            default -> throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        };
    }

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "localhost");
        Session session = Session.getInstance(properties);

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("test@test.com"));
            message.setRecipients(Message.RecipientType.TO,
                    new InternetAddress[]{new InternetAddress(user.getId())});
            message.setSubject("Upgrade 안내");
            message.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");

            Transport.send(message);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
