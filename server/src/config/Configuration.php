<?php
namespace config;

class Configuration
{
    const DEFAULT_API_KEY = 'AAAACqoa_4g:APA91bG3gvGKSvkf9UD7XL1eUdIMW1LQ0imU8VYf_lLqgsdyJGSmVbcImepKxGUqghzoHPjqGoVymu6PnPYjbpqQXoCIj8eNYZO_SgjDa8vaEUmosZC6XBn9-VqHomjDyNifVcR3q368';

    private $apiKey;

    /**
     * Initializes configuration with manually set api key.
     */
    public function __construct()
    {
        $this->apiKey = self::DEFAULT_API_KEY;
        return $this;
    }

    /**
     * add your server api key here
     * read how to obtain an api key here: https://firebase.google.com/docs/server/setup#prerequisites
     *
     * @param string $apiKey
     *
     * @return Configuration
     */
    public function setApiKey($apiKey)
    {
        $this->apiKey = $apiKey;
        return $this;
    }

    public function setDefaultApiKey(){
        $this->apiKey = self::DEFAULT_API_KEY;
    }

    public function getApiKey()
    {
        return $this->apiKey;
    }
}