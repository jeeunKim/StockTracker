package hello.capstone.validation;

import hello.capstone.validation.group.EmailCheckGroup;
import hello.capstone.validation.group.NotBlanckGroup;
import hello.capstone.validation.group.PatternCheckGroup;
import hello.capstone.validation.group.SizeCheckGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({NotBlanckGroup.class,EmailCheckGroup.class,SizeCheckGroup.class ,PatternCheckGroup.class})
public interface ValidationSequence {

}