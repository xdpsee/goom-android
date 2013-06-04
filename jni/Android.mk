LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)  
LOCAL_LDLIBS := -llog -lpng -lz
LOCAL_MODULE    := androidgoom
LOCAL_SRC_FILES := $(filter-out goomsl_hash.c, $(notdir $(wildcard $(LOCAL_PATH)/*.c)))

include $(BUILD_SHARED_LIBRARY)  
