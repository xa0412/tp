package seedu.address;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DateCheckTest {

    private LocalDateTime endPrevSemester2DateTime = LocalDateTime.of(2024, 12, 31, 0, 0);
    private LocalDateTime startSemester1DateTime = LocalDateTime.of(2025, 1, 1, 0, 0);
    private LocalDateTime endSemester1DateTime = LocalDateTime.of(2025, 5, 12, 0, 0);
    private LocalDateTime startSemester2DateTime = LocalDateTime.of(2025, 8, 1, 0, 0);
    private LocalDateTime endSemester2DateTime = LocalDateTime.of(2025, 12, 31, 0, 0);

    @Test
    public void testLastLoginBeforeSem1CurrentBeforeSem1() {
        LocalDateTime lastLogin = LocalDateTime.of(2024, 12, 29, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2024, 12, 30, 0, 0);
        assertFalse(checkLoginPeriod(lastLogin, currentDate, endPrevSemester2DateTime, startSemester1DateTime));
    }

    @Test
    public void testLastLoginBeforeSem1CurrentInSem1() {
        LocalDateTime lastLogin = LocalDateTime.of(2024, 12, 31, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 1, 2, 0, 0);
        assertTrue(checkLoginPeriod(lastLogin, currentDate, endSemester1DateTime, startSemester1DateTime));
    }

    @Test
    public void testLastLoginSem1CurrentSem1() {
        LocalDateTime lastLogin = LocalDateTime.of(2025, 1, 2, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 1, 3, 0, 0);
        assertFalse(checkLoginPeriod(lastLogin, currentDate, endSemester1DateTime, startSemester1DateTime));
    }

    @Test
    public void testLastLoginBeforeSem1CurrentAfterSem1BeforeSem2() {
        LocalDateTime lastLogin = LocalDateTime.of(2024, 12, 30, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 6, 1, 0, 0);
        assertTrue(checkLoginPeriod(lastLogin, currentDate, endSemester1DateTime, startSemester1DateTime));
    }

    @Test
    public void testLastLoginSem1CurrentAfterSem1BeforeSem2() {
        LocalDateTime lastLogin = LocalDateTime.of(2025, 1, 10, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 6, 1, 0, 0);
        assertTrue(checkLoginPeriod(lastLogin, currentDate, endSemester1DateTime, startSemester1DateTime));
    }

    @Test
    public void testLastLoginAfterSem1BeforeSem2CurrentAfterSem1BeforeSem2() {
        LocalDateTime lastLogin = LocalDateTime.of(2025, 6, 1, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 7, 1, 0, 0);
        assertFalse(checkLoginPeriod(lastLogin, currentDate, endSemester1DateTime, startSemester1DateTime));
    }

    @Test
    public void testLastLoginBeforeSem2CurrentAfterSem1BeforeSem2() {
        LocalDateTime lastLogin = LocalDateTime.of(2025, 7, 1, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 7, 20, 0, 0);
        assertFalse(checkLoginPeriod(lastLogin, currentDate, endSemester1DateTime, startSemester1DateTime));
    }

    @Test
    public void testLastLoginAfterSem1BeforeSem2CurrentSem2() {
        LocalDateTime lastLogin = LocalDateTime.of(2025, 7, 3, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 10, 1, 0, 0);
        assertTrue(checkLoginPeriod(lastLogin, currentDate, endSemester2DateTime, startSemester2DateTime));
    }

    @Test
    public void testLastLoginSem2CurrentSem2() {
        LocalDateTime lastLogin = LocalDateTime.of(2025, 10, 1, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 11, 15, 0, 0);
        assertFalse(checkLoginPeriod(lastLogin, currentDate, endSemester2DateTime, startSemester2DateTime));
    }

    @Test
    public void testLastLoginSameAsStartSem() {
        LocalDateTime lastLogin = LocalDateTime.of(2025, 8, 1, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 11, 15, 0, 0);
        assertFalse(checkLoginPeriod(lastLogin, currentDate, endSemester2DateTime, startSemester2DateTime));
    }

    @Test
    public void testLastLoginSameAsEndSem() {
        LocalDateTime lastLogin = LocalDateTime.of(2025, 12, 31, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 12, 1, 0, 0);
        assertFalse(checkLoginPeriod(lastLogin, currentDate, endSemester2DateTime, startSemester2DateTime));
    }

    @Test
    public void testLastLoginSameAsCurrentDate() {
        LocalDateTime lastLogin = LocalDateTime.of(2025, 8, 2, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 8, 2, 0, 0);
        assertFalse(checkLoginPeriod(lastLogin, currentDate, endSemester2DateTime, startSemester2DateTime));
    }

    @Test
    public void testLastLoginBeforeSem1CurrentAfterSem2() {
        LocalDateTime lastLogin = LocalDateTime.of(2024, 12, 31, 0, 0);
        LocalDateTime currentDate = LocalDateTime.of(2025, 10, 1, 0, 0);
        assertTrue(checkLoginPeriod(lastLogin, currentDate, endSemester2DateTime, startSemester2DateTime));
    }

    public boolean checkLoginPeriod(LocalDateTime lastLogin, LocalDateTime currentDateTime,
                                    LocalDateTime endSemesterDateTime, LocalDateTime startSemesterDateTime) {

        if ((currentDateTime.isAfter(endSemesterDateTime) && lastLogin.isBefore(endSemesterDateTime))
                || (lastLogin.isBefore(startSemesterDateTime) && currentDateTime.isAfter(startSemesterDateTime))) {
            return true;
        }
        return false;
    }
}
