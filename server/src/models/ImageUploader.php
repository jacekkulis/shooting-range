<?php
namespace src\models;


class ImageUploader
{
    private $fileHandler;
    private $uploadOk;
    private $target_dir;
    private $target_file;
    private $imageFileType;
    private $fileSize;

    private $uploaded;

    var $result;

    /**
     *
     * Takes $_FILES['form_field'] array as argument
     * where form_field is the form field name

     * @access private
     * @param  array  $file $_FILES['form_field']
     *    or   string $file Local filename
     */
    function  __construct($file)  {
        $this->uploadOk = 1;
        $this->uploaded = false;
        $this->fileHandler = $file;
        $this->target_dir = "../eventImages/";
        $this->target_file = basename($file["name"]);
        $this->imageFileType = pathinfo($this->target_file,PATHINFO_EXTENSION);
        $this->fileSize = $this->fileHandler["size"];
        $this->result = $this->upload($file);
    }

    function getTargetFile(){
        return $this->target_file;
    }

    function isUploaded(){
        if ($this->uploaded){
            return true;
        }
        else {
            return false;
        }
    }

    function upload($file) {
        $uniqueSaveName = time().uniqid(rand());
        $target_file = pathinfo($this->target_file,PATHINFO_FILENAME).$uniqueSaveName.'.'.pathinfo($this->target_file,PATHINFO_EXTENSION);
        $this->target_file = $target_file;

        if ($this->fileHandler["size"] == 0 && $this->fileHandler['error'] == 0)
        {
            $this->result = "There is no file.";
            exit;
        }

        // Check if image file is a actual image or fake image
        if(isset($file)) {
            $check = getimagesize($file["tmp_name"]);
            if($check !== false) {
                $this->result = "File is an image - " . $check["mime"] . ".";
                $this->uploadOk = 1;
            } else {
                $this->result = "File is not an image.";
                $this->uploadOk = 0;
                exit;
            }
        }

        // Check if file already exists
        if (file_exists($this->target_file)) {
            $this->result = "Sorry, file already exists.";
            $this->uploadOk = 0;
            exit;
        }

        // Check file size
        if ($this->fileSize > 10000000) {
            $this->result = "Sorry, your file is too large.";
            $this->uploadOk = 0;
            exit;
        }

        // Allow certain file formats
        if($this->imageFileType != "jpg" && $this->imageFileType != "png" && $this->imageFileType != "jpeg") {
            $this->result = "Sorry, only JPG, JPEG & PNG files are allowed.";
            $this->uploadOk = 0;
            exit;
        }

        // Crop image and make thumbnail
        $image = imagecreatefromstring(file_get_contents($this->fileHandler["tmp_name"]));
        $filename = $this->fileHandler["tmp_name"];

        $thumb_width = 300;
        $thumb_height = 200;

        $width = imagesx($image);
        $height = imagesy($image);

        $original_aspect = $width / $height;
        $thumb_aspect = $thumb_width / $thumb_height;

        if ( $original_aspect >= $thumb_aspect )
        {
            // If image is wider than thumbnail (in aspect ratio sense)
            $new_height = $thumb_height;
            $new_width = $width / ($height / $thumb_height);
        }
        else
        {
            // If the thumbnail is wider than the image
            $new_width = $thumb_width;
            $new_height = $height / ($width / $thumb_width);
        }

        $thumb = imagecreatetruecolor( $thumb_width, $thumb_height );

        // Resize and crop
        imagecopyresampled($thumb,
            $image,
            0 - ($new_width - $thumb_width) / 2, // Center the image horizontally
            0 - ($new_height - $thumb_height) / 2, // Center the image vertically
            0, 0,
            $new_width, $new_height,
            $width, $height);

        imagejpeg($thumb, $filename, 80);


        // Check if $uploadOk is set to 0 by an error
        if ($this->uploadOk == 0) {
            return $this->result;
        // if everything is ok, try to upload file
        } else {
            if (move_uploaded_file($this->fileHandler["tmp_name"], $this->target_dir.$this->target_file)) {
                $this->uploaded = true;
                $this->result = "This image has been uploaded.";
            } else {
                $this->uploaded = false;
                $this->result = "Sorry, there was an error uploading your file.";
            }
        }

        return $this->result;
    }
}