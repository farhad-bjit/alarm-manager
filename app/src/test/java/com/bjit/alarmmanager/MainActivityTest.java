import org.junit.Assert.
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

class MainActivityTest {

    private lateinit var mainActivity: MainActivity
    private lateinit var alarmHelper: AlarmHelper

    @Before
    fun setUp() {
        mainActivity = MainActivity()
        alarmHelper = mock(AlarmHelper::class.java)
        mainActivity.alarmHelper = alarmHelper
    }

    @Test
    fun displayAlarmTimes_withMultipleAlarms_displaysCorrectTimes() {
        val alarmTimes = listOf(1633072800000L, 1633159200000L) // Example timestamps
        `when`(alarmHelper.getAlarmTimes()).thenReturn(alarmTimes)

        mainActivity.displayAlarmTimes()

        val expectedTimes = listOf(
                "Alarm 1: 12:00 AM",
                "Alarm 2: 12:00 AM"
        )
        val adapter = mainActivity.listViewAlarmTimes.adapter as ArrayAdapter<String>
                assertEquals(expectedTimes, adapter.toList())
    }

    @Test
    fun displayAlarmTimes_withNoAlarms_displaysEmptyList() {
        `when`(alarmHelper.getAlarmTimes()).thenReturn(emptyList())

        mainActivity.displayAlarmTimes()

        val adapter = mainActivity.listViewAlarmTimes.adapter as ArrayAdapter<String>
                assertEquals(emptyList<String>(), adapter.toList())
    }

    @Test
    fun displayAlarmTimes_withSingleAlarm_displaysCorrectTime() {
        val alarmTimes = listOf(1633072800000L) // Example timestamp
        `when`(alarmHelper.getAlarmTimes()).thenReturn(alarmTimes)

        mainActivity.displayAlarmTimes()

        val expectedTimes = listOf("Alarm 1: 12:00 AM")
        val adapter = mainActivity.listViewAlarmTimes.adapter as ArrayAdapter<String>
                assertEquals(expectedTimes, adapter.toList())
    }
}