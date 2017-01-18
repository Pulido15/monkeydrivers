package is.monkeydrivers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class Bus_ {

    private Bus bus;
    private List<Subscriber> subscribers = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        bus = new SimpleBus();
    }

    @Test
    public void should_send_a_message_to_a_subscriber() throws Exception {
        Message message = messageOfType("foo");

        String type = "foo";
        Subscriber subscriber = createSubscriberToType(type);

        bus.send(message);
        verify(subscriber).receive(message);
    }


    @Test
    public void should_not_send_a_message_to_a_subscriber_that_is_not_subscribed() throws Exception {
        Message message = messageOfType("faa");
        Subscriber subscriber = createSubscriberToType("foo");
        bus.send(message);
        verify(subscriber, times(0)).receive(message);
    }

    @Test
    public void should_send_a_message_to_all_subscribers() throws Exception {
        createSubscriberToType("foo");
        createSubscriberToType("foo");
        createSubscriberToType("foo");
        createSubscriberToType("foo");

        Message message = messageOfType("foo");
        bus.send(message);

        subscribers.forEach(s->verify(s).receive(message));
    }

    @Test
    public void should_send_only_subscribed_messages_to_a_subscriber() throws Exception {
        Subscriber subscriber = createSubscriberToType("foo");

        bus.send(messageOfType("foo"));
        bus.send(messageOfType("foo"));
        bus.send(messageOfType("faa"));
        bus.send(messageOfType("fii"));
        bus.send(messageOfType("faa"));
        bus.send(messageOfType("foo"));
        bus.send(messageOfType("fii"));
        bus.send(messageOfType("foo"));
        bus.send(messageOfType("faa"));


        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(subscriber,times(4)).receive(captor.capture());
        assertThat(captor.getAllValues().size(), is(4));
        captor.getAllValues().forEach(
                m -> assertThat(m.type(), is("foo"))
        );

    }

    @Test
    public void should_not_send_a_message_to_subscribers_that_are_not_subscribed() throws Exception {
        createSubscriberToType("faa");
        createSubscriberToType("faa");
        createSubscriberToType("faa");
        Subscriber target = createSubscriberToType("faa", "foo");

        Message message = messageOfType("foo");
        bus.send(message);

        subscribers.forEach(s->verify(s,times(s==target ? 1 : 0)).receive(message));
    }

    private Subscriber createSubscriberToType(String... types) {
        Subscriber subscriber = mock(Subscriber.class);
        for (String type : types) bus.subscribe(subscriber).to(type);
        subscribers.add(subscriber);
        return subscriber;
    }

    private Message messageOfType(String type) {
        Message message = mock(Message.class);
        doReturn(type).when(message).type();
        return message;
    }


}
