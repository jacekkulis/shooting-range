<?php
namespace firebase\PhpFirebaseCloudMessaging;
use firebase\PhpFirebaseCloudMessaging\FCMClient;
use \GuzzleHttp\Client;
use config\Configuration;
use firebase\PhpFirebaseCloudMessaging\Message;
use firebase\PhpFirebaseCloudMessaging\Topic;
use firebase\PhpFirebaseCloudMessaging\Notification;

class NotificationSender
{
    private $config;
    private $client;

    public function __construct() {
        $this->config = new Configuration();
        $this->client = new FCMClient();
        $this->client->setApiKey($this->config->getApiKey());
        $this->client->injectGuzzleHttpClient(new Client());
    }

    public function sendNotification($title, $body){
        $message = new Message();
        $message->setPriority('high');
        $message->addRecipient(new \firebase\PhpFirebaseCloudMessaging\Recipient\Topic('events'));
        $message
            ->setNotification(new Notification($title, $body))
            ->setData(['key' => 'value'])
        ;

        return $this->client->send($message);
    }
}